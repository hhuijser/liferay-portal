create unique index IX_D76DD2CF on PollsChoice (questionId, name[$COLUMN_LENGTH:75$]);
create index IX_8AE746EF on PollsChoice (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_C222BD31 on PollsChoice (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_9FF342EA on PollsQuestion (groupId);
create index IX_F910BBB4 on PollsQuestion (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_F3C9F36 on PollsQuestion (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_D5DF7B54 on PollsVote (choiceId);
create index IX_12112599 on PollsVote (questionId);
create index IX_85FBF2D3 on PollsVote (userId, questionId);
create index IX_7D8E92B8 on PollsVote (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_A88C673A on PollsVote (uuid_[$COLUMN_LENGTH:75$], groupId);