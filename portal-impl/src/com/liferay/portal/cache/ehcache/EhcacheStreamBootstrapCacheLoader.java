package com.liferay.portal.cache.ehcache;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InitialThreadLocal;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.bootstrap.BootstrapCacheLoader;
import net.sf.ehcache.distribution.RemoteCacheException;
public class EhcacheStreamBootstrapCacheLoader implements BootstrapCacheLoader {

	public EhcacheStreamBootstrapCacheLoader(Properties properties) {
		if(properties != null) {
			_bootstrap_asynchronously = GetterUtil.getBoolean(
				properties.getProperty(_BOOTSTRAP_ASYNCHRONOUSLY));
		}
	}
	public static void resetSkip() {
		_skipBootstrapThreadLocal.remove();
	}

	public static void setSkip() {
		_skipBootstrapThreadLocal.set(Boolean.TRUE);
	}

	public static synchronized void start() {
		if(!_started) {
			_started = true;
		}
		for(Ehcache ehcache : _deferredEhcache) {
			if(_log.isDebugEnabled()) {
				_log.debug("loading deferred cache: " + ehcache.getName());
			}

			try {
				EhcacheStreamBootstrapHelpUtil.acquireCachePeers(ehcache);
			}
			catch (Exception e) {
				throw new CacheException(e);
			}
		}
	}

	@Override
	public boolean isAsynchronous() {
		return _bootstrap_asynchronously;
	}

	@Override
	public Object clone() {
		return this;
	}
	
	private class StreamBootstrapThread extends Thread {
		public StreamBootstrapThread(Ehcache ehcache) {
			if(_log.isDebugEnabled()) {
				_log.debug("Bootstrap thread for cache " + ehcache.getName() +
					" asynchronously.");
				
			}
			
			_ehcache = ehcache;
			setDaemon(true);
			setPriority(Thread.NORM_PRIORITY);
		}
		
		public void run() {
			try {
				doLoad(_ehcache);
			}
			catch(RemoteCacheException e) {
				if(_log.isWarnEnabled()) {
					_log.warn(
						"Error asynchronously performing streamBootstrap.");
				}
			}
			finally {
				_ehcache = null;
			}
		}

 		private Ehcache _ehcache;
	}

	@Override
	public void load(Ehcache ehcache) {
		if(_bootstrap_asynchronously) {
			StreamBootstrapThread streamBootstrapThread =
				new StreamBootstrapThread(ehcache);
			streamBootstrapThread.start();
		}
		else {
			doLoad(ehcache);
		}
	}

	public void doLoad(Ehcache ehcache) {
		synchronized (EhcacheStreamBootstrapCacheLoader.class) {
			if (!_started) {
				_deferredEhcache.add(ehcache);

				return;
			}
		}

		if (_skipBootstrapThreadLocal.get()) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Bootstraping " + ehcache.getName());
		}

		try {
			EhcacheStreamBootstrapHelpUtil.acquireCachePeers(ehcache);
		}
		catch (Exception e) {
			throw new CacheException(e);
		}
	}

	private static final String _BOOTSTRAP_ASYNCHRONOUSLY =
		"bootstrapAsynchronously";

	private static Log _log = LogFactoryUtil.getLog(
		EhcacheStreamBootstrapCacheLoader.class);
	private static List<Ehcache> _deferredEhcache = new ArrayList<Ehcache>();
	private static ThreadLocal<Boolean> _skipBootstrapThreadLocal = 
		new InitialThreadLocal<Boolean>(
			EhcacheStreamBootstrapCacheLoader.class + 
				"._skipBootstrapThreadLocal",
			false);
	private static boolean _started;
	private boolean _bootstrap_asynchronously = true;

}