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

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import mousio.etcd4j.EtcdClient;
import mousio.etcd4j.promises.EtcdResponsePromise;
import mousio.etcd4j.requests.EtcdKeyDeleteRequest;
import mousio.etcd4j.requests.EtcdKeyGetRequest;
import mousio.etcd4j.requests.EtcdKeyPutRequest;
import mousio.etcd4j.responses.EtcdAuthenticationException;
import mousio.etcd4j.responses.EtcdException;
import mousio.etcd4j.responses.EtcdKeysResponse;

/**
 * @author Michael Hashimoto
 */
public class EtcdUtil {

	public static void delete(String etcdServerURL, Node node) {
		try (EtcdClient etcdClient = getEtcdClient(etcdServerURL)) {
			EtcdKeyDeleteRequest etcdKeyDeleteRequest = null;

			if (!node.isDir()) {
				etcdKeyDeleteRequest = etcdClient.delete(node.getKey());
			}
			else {
				etcdKeyDeleteRequest = etcdClient.deleteDir(node.getKey());

				etcdKeyDeleteRequest.recursive();
			}

			EtcdResponsePromise<EtcdKeysResponse> etcdResponsePromise =
				etcdKeyDeleteRequest.send();

			etcdResponsePromise.get();
		}
		catch (EtcdAuthenticationException | EtcdException | IOException |
			   TimeoutException e) {

			throw new RuntimeException(e);
		}
	}

	public static Node get(String etcdServerURL, String key) {
		try (EtcdClient etcdClient = getEtcdClient(etcdServerURL)) {
			EtcdKeyGetRequest etcdKeyGetRequest = etcdClient.get(key);

			EtcdResponsePromise<EtcdKeysResponse> etcdResponsePromise =
				etcdKeyGetRequest.send();

			EtcdKeysResponse etcdKeysResponse = etcdResponsePromise.get();

			return new Node(etcdServerURL, etcdKeysResponse.getNode());
		}
		catch (EtcdException ee) {
			return null;
		}
		catch (EtcdAuthenticationException | IOException | TimeoutException e) {
			throw new RuntimeException(e);
		}
	}

	public static EtcdClient getEtcdClient(String url) {
		try {
			return new EtcdClient(new URI(url));
		}
		catch (URISyntaxException urise) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to create an Etcd client using url ", url),
				urise);
		}
	}

	public static boolean has(String etcdServerURL, String key) {
		Node node = get(etcdServerURL, key);

		if (node == null) {
			return false;
		}

		return true;
	}

	public static Node put(String etcdServerURL, String key) {
		return put(etcdServerURL, key, null);
	}

	public static Node put(String etcdServerURL, String key, String value) {
		try (EtcdClient etcdClient = getEtcdClient(etcdServerURL)) {
			EtcdKeyPutRequest etcdKeyPutRequest = null;

			if (value == null) {
				etcdKeyPutRequest = etcdClient.putDir(key);
			}
			else {
				etcdKeyPutRequest = etcdClient.put(key, value);
			}

			if (has(etcdServerURL, key)) {
				etcdKeyPutRequest.prevExist(true);
			}

			EtcdResponsePromise<EtcdKeysResponse> etcdResponsePromise =
				etcdKeyPutRequest.send();

			EtcdKeysResponse etcdKeysResponse = etcdResponsePromise.get();

			return new Node(etcdServerURL, etcdKeysResponse.getNode());
		}
		catch (EtcdAuthenticationException | EtcdException | IOException |
			   TimeoutException e) {

			throw new RuntimeException(e);
		}
	}

	public static class Node {

		public long getCreatedIndex() {
			return _etcdNode.getCreatedIndex();
		}

		public String getEtcdServerURL() {
			return _etcdServerURL;
		}

		public String getKey() {
			return _etcdNode.getKey();
		}

		public long getModifiedIndex() {
			_refreshEtcdNode();

			return _etcdNode.getModifiedIndex();
		}

		public int getNodeCount() {
			List<Node> nodes = getNodes();

			return nodes.size();
		}

		public List<Node> getNodes() {
			_refreshEtcdNode();

			List<Node> nodes = new ArrayList<>();

			List<EtcdKeysResponse.EtcdNode> childEtcdNodes =
				_etcdNode.getNodes();

			for (EtcdKeysResponse.EtcdNode childEtcdNode : childEtcdNodes) {
				nodes.add(new Node(_etcdServerURL, childEtcdNode));
			}

			return nodes;
		}

		public String getValue() {
			if (!isDir()) {
				_refreshEtcdNode();

				return _etcdNode.getValue();
			}

			return null;
		}

		public boolean isDir() {
			return _etcdNode.isDir();
		}

		private Node(String etcdServerURL, EtcdKeysResponse.EtcdNode etcdNode) {
			_etcdServerURL = etcdServerURL;
			_etcdNode = etcdNode;
		}

		private void _refreshEtcdNode() {
			try (EtcdClient etcdClient = getEtcdClient(_etcdServerURL)) {
				EtcdKeyGetRequest etcdKeyGetRequest = etcdClient.get(getKey());

				EtcdResponsePromise<EtcdKeysResponse> etcdResponsePromise =
					etcdKeyGetRequest.send();

				EtcdKeysResponse etcdKeysResponse = etcdResponsePromise.get();

				_etcdNode = etcdKeysResponse.getNode();
			}
			catch (EtcdAuthenticationException | EtcdException | IOException |
				   TimeoutException e) {

				throw new RuntimeException(e);
			}
		}

		private EtcdKeysResponse.EtcdNode _etcdNode;
		private final String _etcdServerURL;

	}

}