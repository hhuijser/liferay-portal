/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.servlet;

import com.liferay.petra.nio.CharsetEncoderUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.RandomAccessInputStream;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.SocketException;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ServletResponseUtil {

	public static boolean isClientAbortException(IOException ioe) {
		Class<?> clazz = ioe.getClass();

		String className = clazz.getName();

		if (className.equals(_CLIENT_ABORT_EXCEPTION)) {
			return true;
		}

		return false;
	}

	public static void sendFile(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String fileName,
			byte[] bytes)
		throws IOException {

		sendFile(
			httpServletRequest, httpServletResponse, fileName, bytes, null);
	}

	public static void sendFile(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String fileName,
			byte[] bytes, String contentType)
		throws IOException {

		sendFile(
			httpServletRequest, httpServletResponse, fileName, bytes,
			contentType, null);
	}

	public static void sendFile(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String fileName,
			byte[] bytes, String contentType, String contentDispositionType)
		throws IOException {

		setHeaders(
			httpServletRequest, httpServletResponse, fileName, contentType,
			contentDispositionType);

		write(httpServletResponse, bytes);
	}

	public static void sendFile(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String fileName,
			InputStream inputStream)
		throws IOException {

		sendFile(
			httpServletRequest, httpServletResponse, fileName, inputStream,
			null);
	}

	public static void sendFile(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String fileName,
			InputStream inputStream, long contentLength, String contentType)
		throws IOException {

		sendFile(
			httpServletRequest, httpServletResponse, fileName, inputStream,
			contentLength, contentType, null);
	}

	public static void sendFile(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String fileName,
			InputStream inputStream, long contentLength, String contentType,
			String contentDispositionType)
		throws IOException {

		setHeaders(
			httpServletRequest, httpServletResponse, fileName, contentType,
			contentDispositionType);

		write(httpServletResponse, inputStream, contentLength);
	}

	public static void sendFile(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String fileName,
			InputStream inputStream, String contentType)
		throws IOException {

		sendFile(
			httpServletRequest, httpServletResponse, fileName, inputStream, 0,
			contentType);
	}

	public static void sendFileWithRangeHeader(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String fileName,
			InputStream inputStream, long contentLength, String contentType)
		throws IOException {

		if (_log.isDebugEnabled()) {
			_log.debug("Accepting ranges for the file " + fileName);
		}

		httpServletResponse.setHeader(
			HttpHeaders.ACCEPT_RANGES, HttpHeaders.ACCEPT_RANGES_BYTES_VALUE);

		List<Range> ranges = null;

		try {
			ranges = _getRanges(httpServletRequest, contentLength);
		}
		catch (IOException ioe) {
			_log.error("Unable to get ranges", ioe);

			httpServletResponse.setHeader(
				HttpHeaders.CONTENT_RANGE, "bytes */" + contentLength);

			httpServletResponse.sendError(
				HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);

			return;
		}

		if ((ranges == null) || ranges.isEmpty()) {
			sendFile(
				httpServletRequest, httpServletResponse, fileName, inputStream,
				contentLength, contentType);
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Request has range header " +
						httpServletRequest.getHeader(HttpHeaders.RANGE));
			}

			_write(
				httpServletRequest, httpServletResponse, fileName, ranges,
				inputStream, contentLength, contentType);
		}
	}

	public static void write(
			HttpServletResponse httpServletResponse,
			BufferCacheServletResponse bufferCacheServletResponse)
		throws IOException {

		if (bufferCacheServletResponse.isByteMode()) {
			write(
				httpServletResponse,
				bufferCacheServletResponse.getByteBuffer());
		}
		else if (bufferCacheServletResponse.isCharMode()) {
			write(
				httpServletResponse,
				bufferCacheServletResponse.getCharBuffer());
		}
	}

	public static void write(
			HttpServletResponse httpServletResponse, byte[] bytes)
		throws IOException {

		write(httpServletResponse, bytes, 0, 0);
	}

	public static void write(
			HttpServletResponse httpServletResponse, byte[] bytes, int offset,
			int contentLength)
		throws IOException {

		try {

			// LEP-3122

			if (!httpServletResponse.isCommitted()) {

				// LEP-536

				if (contentLength == 0) {
					contentLength = bytes.length;
				}

				httpServletResponse.setContentLength(contentLength);

				httpServletResponse.flushBuffer();

				if (httpServletResponse instanceof BufferCacheServletResponse) {
					BufferCacheServletResponse bufferCacheServletResponse =
						(BufferCacheServletResponse)httpServletResponse;

					bufferCacheServletResponse.setByteBuffer(
						ByteBuffer.wrap(bytes, offset, contentLength));
				}
				else {
					if ((contentLength == 0) && ServerDetector.isJetty()) {
					}
					else {
						ServletOutputStream servletOutputStream =
							httpServletResponse.getOutputStream();

						servletOutputStream.write(bytes, offset, contentLength);
					}
				}
			}
		}
		catch (IOException ioe) {
			if ((ioe instanceof SocketException) ||
				isClientAbortException(ioe)) {

				if (_log.isWarnEnabled()) {
					_log.warn(ioe, ioe);
				}
			}
			else {
				throw ioe;
			}
		}
	}

	public static void write(
			HttpServletResponse httpServletResponse, byte[][] bytesArray)
		throws IOException {

		try {

			// LEP-3122

			if (!httpServletResponse.isCommitted()) {
				long contentLength = 0;

				for (byte[] bytes : bytesArray) {
					contentLength += bytes.length;
				}

				setContentLength(httpServletResponse, contentLength);

				httpServletResponse.flushBuffer();

				ServletOutputStream servletOutputStream =
					httpServletResponse.getOutputStream();

				for (byte[] bytes : bytesArray) {
					servletOutputStream.write(bytes);
				}
			}
		}
		catch (IOException ioe) {
			if ((ioe instanceof SocketException) ||
				isClientAbortException(ioe)) {

				if (_log.isWarnEnabled()) {
					_log.warn(ioe, ioe);
				}
			}
			else {
				throw ioe;
			}
		}
	}

	public static void write(
			HttpServletResponse httpServletResponse, ByteBuffer byteBuffer)
		throws IOException {

		if (httpServletResponse instanceof BufferCacheServletResponse) {
			BufferCacheServletResponse bufferCacheServletResponse =
				(BufferCacheServletResponse)httpServletResponse;

			bufferCacheServletResponse.setByteBuffer(byteBuffer);
		}
		else {
			write(
				httpServletResponse, byteBuffer.array(),
				byteBuffer.arrayOffset() + byteBuffer.position(),
				byteBuffer.arrayOffset() + byteBuffer.limit());
		}
	}

	public static void write(
			HttpServletResponse httpServletResponse, CharBuffer charBuffer)
		throws IOException {

		if (httpServletResponse instanceof BufferCacheServletResponse) {
			BufferCacheServletResponse bufferCacheServletResponse =
				(BufferCacheServletResponse)httpServletResponse;

			bufferCacheServletResponse.setCharBuffer(charBuffer);
		}
		else {
			ByteBuffer byteBuffer = CharsetEncoderUtil.encode(
				StringPool.UTF8, charBuffer);

			write(httpServletResponse, byteBuffer);
		}
	}

	public static void write(HttpServletResponse httpServletResponse, File file)
		throws IOException {

		if (httpServletResponse instanceof BufferCacheServletResponse) {
			BufferCacheServletResponse bufferCacheServletResponse =
				(BufferCacheServletResponse)httpServletResponse;

			ByteBuffer byteBuffer = ByteBuffer.wrap(FileUtil.getBytes(file));

			bufferCacheServletResponse.setByteBuffer(byteBuffer);
		}
		else {
			FileInputStream fileInputStream = new FileInputStream(file);

			try (FileChannel fileChannel = fileInputStream.getChannel()) {
				long contentLength = fileChannel.size();

				setContentLength(httpServletResponse, contentLength);

				httpServletResponse.flushBuffer();

				fileChannel.transferTo(
					0, contentLength,
					Channels.newChannel(httpServletResponse.getOutputStream()));
			}
		}
	}

	public static void write(
			HttpServletResponse httpServletResponse, InputStream inputStream)
		throws IOException {

		write(httpServletResponse, inputStream, 0);
	}

	public static void write(
			HttpServletResponse httpServletResponse, InputStream inputStream,
			long contentLength)
		throws IOException {

		if (httpServletResponse.isCommitted()) {
			if (inputStream != null) {
				try {
					inputStream.close();
				}
				catch (IOException ioe) {
					if (_log.isWarnEnabled()) {
						_log.warn(ioe, ioe);
					}
				}
			}

			return;
		}

		if (contentLength > 0) {
			httpServletResponse.setHeader(
				HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength));
		}

		httpServletResponse.flushBuffer();

		StreamUtil.transfer(inputStream, httpServletResponse.getOutputStream());
	}

	public static void write(HttpServletResponse httpServletResponse, String s)
		throws IOException {

		if (httpServletResponse instanceof BufferCacheServletResponse) {
			BufferCacheServletResponse bufferCacheServletResponse =
				(BufferCacheServletResponse)httpServletResponse;

			bufferCacheServletResponse.setString(s);
		}
		else {
			ByteBuffer byteBuffer = CharsetEncoderUtil.encode(
				StringPool.UTF8, CharBuffer.wrap(s));

			write(httpServletResponse, byteBuffer);
		}
	}

	protected static void setContentLength(
		HttpServletResponse httpServletResponse, long contentLength) {

		httpServletResponse.setHeader(
			HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength));
	}

	protected static void setHeaders(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String fileName,
		String contentType, String contentDispositionType) {

		if (_log.isDebugEnabled()) {
			_log.debug("Sending file of type " + contentType);
		}

		// LEP-2201

		if (Validator.isNotNull(contentType)) {
			httpServletResponse.setContentType(contentType);
		}

		if (!httpServletResponse.containsHeader(HttpHeaders.CACHE_CONTROL)) {
			httpServletResponse.setHeader(
				HttpHeaders.CACHE_CONTROL,
				HttpHeaders.CACHE_CONTROL_PRIVATE_VALUE);
		}

		if (Validator.isNull(fileName)) {
			return;
		}

		String contentDispositionFileName = "filename=\"" + fileName + "\"";

		// If necessary for non-ASCII characters, encode based on RFC 2184.
		// However, not all browsers support RFC 2184. See LEP-3127.

		boolean ascii = true;

		for (int i = 0; i < fileName.length(); i++) {
			if (!Validator.isAscii(fileName.charAt(i))) {
				ascii = false;

				break;
			}
		}

		if (!ascii) {
			String encodedFileName = URLCodec.encodeURL(fileName, true);

			if (BrowserSnifferUtil.isIe(httpServletRequest)) {
				contentDispositionFileName =
					"filename=\"" + encodedFileName + "\"";
			}
			else {
				contentDispositionFileName =
					"filename*=UTF-8''" + encodedFileName;
			}
		}

		if (Validator.isNull(contentDispositionType)) {
			String extension = GetterUtil.getString(
				FileUtil.getExtension(fileName));

			extension = StringUtil.toLowerCase(extension);

			String[] mimeTypesContentDispositionInline = null;

			try {
				mimeTypesContentDispositionInline = PropsUtil.getArray(
					PropsKeys.MIME_TYPES_CONTENT_DISPOSITION_INLINE);
			}
			catch (Exception e) {
				mimeTypesContentDispositionInline = new String[0];
			}

			if (ArrayUtil.contains(
					mimeTypesContentDispositionInline, extension)) {

				contentDispositionType = HttpHeaders.CONTENT_DISPOSITION_INLINE;

				contentType = MimeTypesUtil.getExtensionContentType(extension);

				httpServletResponse.setContentType(contentType);
			}
			else {
				contentDispositionType =
					HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT;
			}
		}

		StringBundler sb = new StringBundler(4);

		sb.append(contentDispositionType);
		sb.append(StringPool.SEMICOLON);
		sb.append(StringPool.SPACE);
		sb.append(contentDispositionFileName);

		if (_log.isDebugEnabled()) {
			_log.debug("Setting content disposition header " + sb.toString());
		}

		httpServletResponse.setHeader(
			HttpHeaders.CONTENT_DISPOSITION, sb.toString());
	}

	protected static void setHeaders(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String fileName,
		String contentType, String contentDispositionType, Range range) {

		setHeaders(
			httpServletRequest, httpServletResponse, fileName, contentType,
			contentDispositionType);

		if (range != null) {
			httpServletResponse.setHeader(
				HttpHeaders.CONTENT_RANGE, range.getContentRange());

			httpServletResponse.setHeader(
				HttpHeaders.CONTENT_LENGTH, String.valueOf(range.getLength()));
		}
	}

	private static InputStream _copyRange(
			InputStream inputStream, OutputStream outputStream, long start,
			long length)
		throws IOException {

		if (inputStream instanceof ByteArrayInputStream) {
			ByteArrayInputStream byteArrayInputStream =
				(ByteArrayInputStream)inputStream;

			byteArrayInputStream.reset();

			byteArrayInputStream.skip(start);

			StreamUtil.transfer(
				byteArrayInputStream, outputStream, StreamUtil.BUFFER_SIZE,
				false, length);

			return byteArrayInputStream;
		}
		else if (inputStream instanceof FileInputStream) {
			FileInputStream fileInputStream = (FileInputStream)inputStream;

			FileChannel fileChannel = fileInputStream.getChannel();

			fileChannel.transferTo(
				start, length, Channels.newChannel(outputStream));

			return fileInputStream;
		}
		else if (inputStream instanceof RandomAccessInputStream) {
			RandomAccessInputStream randomAccessInputStream =
				(RandomAccessInputStream)inputStream;

			randomAccessInputStream.seek(start);

			StreamUtil.transfer(
				randomAccessInputStream, outputStream, StreamUtil.BUFFER_SIZE,
				false, length);

			return randomAccessInputStream;
		}

		inputStream.skip(start);

		StreamUtil.transfer(
			inputStream, outputStream, StreamUtil.BUFFER_SIZE, false, length);

		return inputStream;
	}

	private static List<Range> _getRanges(
			HttpServletRequest httpServletRequest, long length)
		throws IOException {

		String rangeString = httpServletRequest.getHeader(HttpHeaders.RANGE);

		if (Validator.isNull(rangeString)) {
			return Collections.emptyList();
		}

		if (!rangeString.matches(_RANGE_REGEX)) {
			throw new IOException(
				"Range header does not match regular expression " +
					rangeString);
		}

		List<Range> ranges = new ArrayList<>();

		String[] rangeFields = StringUtil.split(rangeString.substring(6));

		if (rangeFields.length > _MAX_RANGE_FIELDS) {
			StringBundler sb = new StringBundler(8);

			sb.append("Request range ");
			sb.append(rangeString);
			sb.append(" with ");
			sb.append(rangeFields.length);
			sb.append(" range fields has exceeded maximum allowance as ");
			sb.append("specified by the property \"");
			sb.append(PropsKeys.WEB_SERVER_SERVLET_MAX_RANGE_FIELDS);
			sb.append("\"");

			throw new IOException(sb.toString());
		}

		for (String rangeField : rangeFields) {
			int index = rangeField.indexOf(StringPool.DASH);

			long start = GetterUtil.getLong(rangeField.substring(0, index), -1);
			long end = GetterUtil.getLong(rangeField.substring(index + 1), -1);

			if (start == -1) {
				start = length - end;
				end = length - 1;
			}
			else if ((end == -1) || (end > (length - 1))) {
				end = length - 1;
			}

			if (start > end) {
				throw new IOException(
					StringBundler.concat(
						"Range start ", String.valueOf(start),
						" is greater than end ", String.valueOf(end)));
			}

			Range range = new Range(start, end, length);

			ranges.add(range);
		}

		return ranges;
	}

	private static boolean _isRandomAccessSupported(InputStream inputStream) {
		if (inputStream instanceof ByteArrayInputStream ||
			inputStream instanceof FileInputStream ||
			inputStream instanceof RandomAccessInputStream) {

			return true;
		}

		return false;
	}

	private static boolean _isSequentialRangeList(List<Range> ranges) {
		Range previousRange = null;

		for (Range range : ranges) {
			if ((previousRange != null) &&
				(range.getStart() <= previousRange.getEnd())) {

				return false;
			}

			previousRange = range;
		}

		return true;
	}

	private static InputStream _toRandomAccessInputStream(
			InputStream inputStream)
		throws IOException {

		if (_isRandomAccessSupported(inputStream)) {
			return inputStream;
		}

		return new RandomAccessInputStream(inputStream);
	}

	private static void _write(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String fileName,
			List<Range> ranges, InputStream inputStream, long fullLength,
			String contentType)
		throws IOException {

		try (OutputStream outputStream =
				httpServletResponse.getOutputStream()) {

			Range fullRange = new Range(0, fullLength - 1, fullLength);

			Range firstRange = null;

			if (!ranges.isEmpty()) {
				firstRange = ranges.get(0);
			}

			if ((firstRange == null) || firstRange.equals(fullRange)) {
				if (_log.isDebugEnabled()) {
					_log.debug("Writing full range");
				}

				httpServletResponse.setContentType(contentType);

				setHeaders(
					httpServletRequest, httpServletResponse, fileName,
					contentType, null, fullRange);

				_copyRange(
					inputStream, outputStream, fullRange.getStart(),
					fullRange.getLength());
			}
			else if (ranges.size() == 1) {
				if (_log.isDebugEnabled()) {
					_log.debug("Attempting to write a single range");
				}

				Range range = ranges.get(0);

				httpServletResponse.setContentType(contentType);

				setHeaders(
					httpServletRequest, httpServletResponse, fileName,
					contentType, null, range);

				httpServletResponse.setStatus(
					HttpServletResponse.SC_PARTIAL_CONTENT);

				_copyRange(
					inputStream, outputStream, range.getStart(),
					range.getLength());
			}
			else if (ranges.size() > 1) {
				if (_log.isDebugEnabled()) {
					_log.debug("Attempting to write multiple ranges");
				}

				ServletOutputStream servletOutputStream =
					(ServletOutputStream)outputStream;

				String boundary =
					"liferay-multipart-boundary-" + System.currentTimeMillis();

				String multipartContentType =
					"multipart/byteranges; boundary=" + boundary;

				httpServletResponse.setContentType(multipartContentType);

				setHeaders(
					httpServletRequest, httpServletResponse, fileName,
					multipartContentType, null);

				httpServletResponse.setStatus(
					HttpServletResponse.SC_PARTIAL_CONTENT);

				boolean sequentialRangeList = _isSequentialRangeList(ranges);

				if (!sequentialRangeList) {
					inputStream = _toRandomAccessInputStream(inputStream);
				}

				Range previousRange = null;

				for (Range curRange : ranges) {
					servletOutputStream.println();
					servletOutputStream.println(
						StringPool.DOUBLE_DASH + boundary);
					servletOutputStream.println(
						HttpHeaders.CONTENT_TYPE + ": " + contentType);
					servletOutputStream.println(
						HttpHeaders.CONTENT_RANGE + ": " +
							curRange.getContentRange());
					servletOutputStream.println();

					long start = curRange.getStart();

					if (sequentialRangeList) {
						if (previousRange != null) {
							start -= previousRange.getEnd() + 1;
						}

						previousRange = curRange;
					}

					_copyRange(
						inputStream, servletOutputStream, start,
						curRange.getLength());
				}

				servletOutputStream.println();
				servletOutputStream.println(
					StringPool.DOUBLE_DASH + boundary + StringPool.DOUBLE_DASH);
			}
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	private static final String _CLIENT_ABORT_EXCEPTION =
		"org.apache.catalina.connector.ClientAbortException";

	private static final int _MAX_RANGE_FIELDS = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.WEB_SERVER_SERVLET_MAX_RANGE_FIELDS));

	private static final String _RANGE_REGEX =
		"^bytes=\\d*-\\d*(,\\s?\\d*-\\d*)*$";

	private static final Log _log = LogFactoryUtil.getLog(
		ServletResponseUtil.class);

}