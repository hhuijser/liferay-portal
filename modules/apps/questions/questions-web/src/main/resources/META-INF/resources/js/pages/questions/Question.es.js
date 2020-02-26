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

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import {Editor} from 'frontend-editor-ckeditor-web';
import React, {useCallback, useContext, useEffect, useState} from 'react';
import {Link} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Answer from '../../components/Answer.es';
import ArticleBodyRenderer from '../../components/ArticleBodyRenderer.es';
import CreatorRow from '../../components/CreatorRow.es';
import Rating from '../../components/Rating.es';
import RelatedQuestions from '../../components/RelatedQuestions.es';
import Subscription from '../../components/Subscription.es';
import TagList from '../../components/TagList.es';
import {
	createAnswer,
	getThread,
	markAsAnswerMessageBoardMessage,
} from '../../utils/client.es';
import lang from '../../utils/lang.es';
import {
	dateToBriefInternationalHuman,
	getCKEditorConfig,
	onBeforeLoadCKEditor,
} from '../../utils/utils.es';

export default ({
	location: key,
	match: {
		params: {questionId},
	},
}) => {
	const context = useContext(AppContext);

	const [answers, setAnswers] = useState([]);
	const [articleBody, setArticleBody] = useState();
	const [page, setPage] = useState(1);
	const [question, setQuestion] = useState();

	useEffect(() => {
		loadThread();
	}, [key, loadThread]);

	const loadThread = useCallback(
		() =>
			getThread(questionId, page).then(data => {
				setQuestion(data);
				setAnswers(data.messageBoardMessages.items);
			}),
		[page, questionId]
	);

	const postAnswer = () => {
		createAnswer(articleBody, question.id).then(() => {
			setArticleBody('');

			return loadThread();
		});
	};

	const deleteAnswer = useCallback(
		answer => {
			setAnswers([
				...answers.filter(otherAnswer => answer.id !== otherAnswer.id),
			]);
		},
		[answers]
	);

	const answerChange = useCallback(
		answerId => {
			const answer = answers.find(
				answer => answer.showAsAnswer && answer.id !== answerId
			);

			if (answer) {
				markAsAnswerMessageBoardMessage(answer.id, false).then(() => {
					setAnswers([
						...answers.map(otherAnswer => {
							otherAnswer.showAsAnswer =
								otherAnswer.id === answerId;

							return otherAnswer;
						}),
					]);
				});
			}
		},
		[answers]
	);

	return (
		<section>
			{question && (
				<div className="autofit-padded autofit-row">
					<div className="autofit-col">
						<Rating
							aggregateRating={question.aggregateRating}
							entityId={question.id}
							myRating={
								question.myRating &&
								question.myRating.ratingValue
							}
							type={'Thread'}
						/>
					</div>

					<div className="autofit-col autofit-col-expand">
						<div className="autofit-section">
							<div className="autofit-row">
								<div className="autofit-col-expand">
									<h1 className="question-headline">
										{question.headline}
									</h1>
									<p>
										<small>
											{Liferay.Language.get('asked')}{' '}
											{dateToBriefInternationalHuman(
												question.dateCreated
											)}
											{' - '}
											{Liferay.Language.get(
												'active'
											)}{' '}
											{dateToBriefInternationalHuman(
												question.dateModified
											)}
											{' - '}
											{lang.sub(
												Liferay.Language.get(
													'viewed-x-times'
												),
												[question.viewCount]
											)}
										</small>
									</p>
								</div>
								<div>
									<ClayButton.Group spaced={true}>
										{question.actions.subscribe && (
											<Subscription
												onSubscription={subscribed =>
													setQuestion({
														...question,
														subscribed,
													})
												}
												question={question}
											/>
										)}
										{question.actions.replace && (
											<Link
												to={`/questions/${questionId}/edit`}
											>
												<ClayButton className="btn btn-secondary">
													{Liferay.Language.get(
														'edit'
													)}
												</ClayButton>
											</Link>
										)}
									</ClayButton.Group>
								</div>
							</div>
							<div>
								<ArticleBodyRenderer {...question} />
							</div>

							<TagList tags={question.keywords} />
						</div>

						<div
							className="autofit-row"
							style={{alignItems: 'center'}}
						>
							<div className="autofit-col-expand">
								<hr />
							</div>
							<div>
								<CreatorRow question={question} />
							</div>
						</div>

						<h3 className="subtitle">
							{answers.length} {Liferay.Language.get('answers')}
						</h3>

						{answers.map(answer => (
							<Answer
								answer={answer}
								answerChange={answerChange}
								deleteAnswer={deleteAnswer}
								key={answer.id}
							/>
						))}

						{!!answers.totalCount &&
							answers.totalCount > answers.pageSize && (
								<ClayPaginationWithBasicItems
									activePage={page}
									ellipsisBuffer={2}
									onPageChange={setPage}
									totalPages={Math.ceil(
										answers.totalCount / answers.pageSize
									)}
								/>
							)}

						{context.canCreateThread && (
							<>
								<ClayForm>
									<ClayForm.Group className="form-group-sm">
										<label htmlFor="basicInput">
											{Liferay.Language.get(
												'your-answer'
											)}
											<span className="reference-mark">
												<ClayIcon symbol="asterisk" />
											</span>
										</label>

										<Editor
											config={getCKEditorConfig()}
											data={articleBody}
											onBeforeLoad={onBeforeLoadCKEditor}
											onChange={event =>
												setArticleBody(
													event.editor.getData()
												)
											}
										/>
									</ClayForm.Group>
								</ClayForm>

								<div className="sheet-footer">
									<div className="btn-group-item">
										<div className="btn-group-item">
											<button
												className="btn btn-primary"
												disabled={!articleBody}
												onClick={postAnswer}
											>
												{Liferay.Language.get(
													'post-answer'
												)}
											</button>
										</div>
									</div>
								</div>
							</>
						)}
					</div>
				</div>
			)}
			{question && question.id && (
				<RelatedQuestions question={question} />
			)}
		</section>
	);
};
