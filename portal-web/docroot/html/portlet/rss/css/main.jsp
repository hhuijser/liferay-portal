<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/portlet/css_init.jsp" %>

.portlet-rss {
	.feed-date {
		background: url(<%= themeImagesPath %>/common/time.png) no-repeat 0 50%;
		margin: 0.3em 0;
		padding-left: 20px;
	}

	.feed-description {
	 	margin: 0 0 5px 0;
	}

	.feed-entries {
		font-size: 1.1em;
	}

	.feed-entry {
		margin-bottom: 4px;
	}

	.feed-entry-author {
		display: block;
		font-size: 0.9em;
	}

	.feed-entry-content {
		padding-left: 1.8em;

		img {
			margin-right: 0.5em;
		}
	}

	.feed-entry-enclosure {
		display: block;
		font-size: 0.9em;
	}
	.feed-entry-expander {
		cursor: pointer;
		float: left;
		padding-right: 5px;
	}

	.feed-entry-title {
		display: block;
		font-weight: bold;
	}

	.feed-image-left {
	 	margin: 4px 0 4px 20px;
	 	text-align: left;
	}

	.feed-image-right {
		float: right;
	 	margin: 0 0 4px 4px;
	}

	.feed-published-date {
		&.feed-date {
			background-image: url(<%= themeImagesPath %>/common/date.png)
		}

		a {
			font-weight: bold;
			text-decoration: none;
		}
	}

	.feed-title {
		font-size: 1.2em;
		font-weight: bold;
		margin: 0 0 0.5em -20px;

		a {
			background: url(<%= themeImagesPath %>/common/news.png) no-repeat 0 50%;
			padding-left: 20px;
		}
	}

	.feed {
		padding-left: 20px;

		.separator {
			margin-left: -20px;
		}
	}

	.header {
		cursor: default;
		font-weight: bold;
		margin-top: 2px;
		padding: 2px 5px;
	}
}