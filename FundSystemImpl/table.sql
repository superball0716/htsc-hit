use htsc;

drop table if exists fund;

create table fund(
	pId int(11) primary key auto_increment,
	identifier varchar(50),
    pname varchar(50),
    description varchar(200),
    worth Decimal(30,10),
    startPoint Decimal(30,10),
    profitRate Decimal(30,10),
    productnum int(11),
    version_id int(11)
)DEFAULT CHARSET=utf8;

drop table if exists FundUser;

create table FundUser(
	uId int(11) primary key auto_increment,
    loginId varchar(50),
    uName varchar(50),
    passwd varchar(50),
    role int(2),
    version_id int(11)
)DEFAULT CHARSET=utf8;

drop table if exists manager;

create table manager(
	managerId int (11) primary key auto_increment,
    mName varchar(50),
    jobId varchar(50),
    decription varchar(500),
    img_url varchar(100),
    version_id int(11)
)DEFAULT CHARSET = utf8;

drop table if exists FundManage;

create table FundManage(
	fId int(11) primary key auto_increment,
    managerId int(11),
    pId int(11),  
	foreign key(managerId) references manager(managerId),
	foreign key(pId) references fund(pId)

)DEFAULT CHARSET=utf8;


create table secKill(
	sId int(11) primary key auto_increment,
    pId int(11),
    createTime datetime,
    startTime datetime,
    endTime datetime,
    foreign key(pId) references fund(pId)
)DEFAULT CHARSET=utf8;


create table secKillResult(
	secResultId int(11) primary key auto_increment,
	uId int(11),
    pId int(11),
    createTime datetime,
    foreign key(uId) references FundUser(uId),
    foreign key(pId) references fund(pId)
)DEFAULT CHARSET=utf8;



create index pId on fund(pId); 

create index identifier on fund(identifier);

create index managerId on FundManage(managerId); 

create index pId on FundManage(pId);

create index loginId on FundUser(loginId); 

create index jobId on manager(jobId);

create index pId on secKill(pId);
