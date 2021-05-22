/*
 Navicat Premium Data Transfer

 Source Server         : mssql
 Source Server Type    : SQL Server
 Source Server Version : 15004123
 Source Host           : localhost
 Source Database       : OP_TRACKER
 Source Schema         : hess_opportunity

 Target Server Type    : SQL Server
 Target Server Version : 15004123
 File Encoding         : utf-8

 Date: 05/18/2021 08:08:10 AM
*/

-- ----------------------------
--  Table structure for discussion_post
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[hess_opportunity].[discussion_post]') AND type IN ('U'))
	DROP TABLE [hess_opportunity].[discussion_post]
GO
CREATE TABLE [hess_opportunity].[discussion_post] (
	[id] int IDENTITY(1,1) NOT NULL,
	[opportunity_id] int NOT NULL,
	[author_id] int NOT NULL,
	[parent_id] int NULL,
	[content] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	[created_by] int NOT NULL,
	[updated_by] int NULL,
	[created_on] datetimeoffset(7) NULL,
	[updated_on] datetimeoffset(7) NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Table structure for document
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[hess_opportunity].[document]') AND type IN ('U'))
	DROP TABLE [hess_opportunity].[document]
GO
CREATE TABLE [hess_opportunity].[document] (
	[id] int NOT NULL,
	[name] nvarchar(128) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	[document_type] nvarchar(32) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	[location] nvarchar(128) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Records of document
-- ----------------------------
BEGIN TRANSACTION
GO
INSERT INTO [hess_opportunity].[document] VALUES ('1', 'document_1', 'pdf', 'location 1');
INSERT INTO [hess_opportunity].[document] VALUES ('2', 'document_2', 'doc', 'location 2');
INSERT INTO [hess_opportunity].[document] VALUES ('3', 'document_3', 'image', 'location 3');
GO
COMMIT
GO

-- ----------------------------
--  Table structure for opportunity
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[hess_opportunity].[opportunity]') AND type IN ('U'))
	DROP TABLE [hess_opportunity].[opportunity]
GO
CREATE TABLE [hess_opportunity].[opportunity] (
	[id] int IDENTITY(1,1) NOT NULL,
	[name] nvarchar(256) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	[tech_theme_id] int NOT NULL,
	[source_id] int NOT NULL,
	[description] nvarchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	[company] nvarchar(128) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	[owner_id] int NOT NULL,
	[created_by] int NOT NULL,
	[updated_by] int NULL,
	[created_on] datetimeoffset(7) NULL,
	[updated_on] datetimeoffset(7) NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Table structure for opportunity_document
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[hess_opportunity].[opportunity_document]') AND type IN ('U'))
	DROP TABLE [hess_opportunity].[opportunity_document]
GO
CREATE TABLE [hess_opportunity].[opportunity_document] (
	[opportunity_id] int NOT NULL,
	[document_id] int NOT NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Table structure for opportunity_link
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[hess_opportunity].[opportunity_link]') AND type IN ('U'))
	DROP TABLE [hess_opportunity].[opportunity_link]
GO
CREATE TABLE [hess_opportunity].[opportunity_link] (
	[id] int IDENTITY(1,1) NOT NULL,
	[opportunity_id] int NOT NULL,
	[link] nvarchar(256) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Table structure for opportunity_members
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[hess_opportunity].[opportunity_members]') AND type IN ('U'))
	DROP TABLE [hess_opportunity].[opportunity_members]
GO
CREATE TABLE [hess_opportunity].[opportunity_members] (
	[opportunity_id] int NOT NULL,
	[user_id] int NOT NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Table structure for opportunity_phase
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[hess_opportunity].[opportunity_phase]') AND type IN ('U'))
	DROP TABLE [hess_opportunity].[opportunity_phase]
GO
CREATE TABLE [hess_opportunity].[opportunity_phase] (
	[id] int IDENTITY(1,1) NOT NULL,
	[opportunity_id] int NOT NULL,
	[status] nvarchar(128) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	[start_date] datetimeoffset(7) NULL,
	[end_date] datetimeoffset(7) NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Table structure for opportunity_tag
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[hess_opportunity].[opportunity_tag]') AND type IN ('U'))
	DROP TABLE [hess_opportunity].[opportunity_tag]
GO
CREATE TABLE [hess_opportunity].[opportunity_tag] (
	[opportunity_id] int NOT NULL,
	[tag_id] int NOT NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Table structure for opportunity_views
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[hess_opportunity].[opportunity_views]') AND type IN ('U'))
	DROP TABLE [hess_opportunity].[opportunity_views]
GO
CREATE TABLE [hess_opportunity].[opportunity_views] (
	[opportunity_id] int NOT NULL,
	[user_id] int NOT NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Table structure for source
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[hess_opportunity].[source]') AND type IN ('U'))
	DROP TABLE [hess_opportunity].[source]
GO
CREATE TABLE [hess_opportunity].[source] (
	[id] int NOT NULL,
	[name] nvarchar(128) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Records of source
-- ----------------------------
BEGIN TRANSACTION
GO
INSERT INTO [hess_opportunity].[source] VALUES ('1', 'source_1');
INSERT INTO [hess_opportunity].[source] VALUES ('2', 'source_2');
INSERT INTO [hess_opportunity].[source] VALUES ('3', 'source_3');
GO
COMMIT
GO

-- ----------------------------
--  Table structure for tag
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[hess_opportunity].[tag]') AND type IN ('U'))
	DROP TABLE [hess_opportunity].[tag]
GO
CREATE TABLE [hess_opportunity].[tag] (
	[id] int NOT NULL,
	[label] nvarchar(128) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Records of tag
-- ----------------------------
BEGIN TRANSACTION
GO
INSERT INTO [hess_opportunity].[tag] VALUES ('1', 'tag_1');
INSERT INTO [hess_opportunity].[tag] VALUES ('2', 'tag_2');
INSERT INTO [hess_opportunity].[tag] VALUES ('3', 'tag_3');
GO
COMMIT
GO

-- ----------------------------
--  Table structure for tech_theme
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[hess_opportunity].[tech_theme]') AND type IN ('U'))
	DROP TABLE [hess_opportunity].[tech_theme]
GO
CREATE TABLE [hess_opportunity].[tech_theme] (
	[id] int NOT NULL,
	[icon] nvarchar(128) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	[name] nvarchar(128) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Records of tech_theme
-- ----------------------------
BEGIN TRANSACTION
GO
INSERT INTO [hess_opportunity].[tech_theme] VALUES ('1', '1.png', 'theme_1');
INSERT INTO [hess_opportunity].[tech_theme] VALUES ('2', '2.png', 'theme_2');
INSERT INTO [hess_opportunity].[tech_theme] VALUES ('3', '3.png', 'theme_3');
GO
COMMIT
GO

-- ----------------------------
--  Table structure for user
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID('[hess_opportunity].[user]') AND type IN ('U'))
	DROP TABLE [hess_opportunity].[user]
GO
CREATE TABLE [hess_opportunity].[user] (
	[id] int NOT NULL,
	[name] nvarchar(128) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	[profile_picture] nvarchar(128) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	[okta_username] nvarchar(45) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL
)
ON [PRIMARY]
GO

-- ----------------------------
--  Records of user
-- ----------------------------
BEGIN TRANSACTION
GO
INSERT INTO [hess_opportunity].[user] VALUES ('1', 'user_1', 'p_1.png', 'okname_1');
INSERT INTO [hess_opportunity].[user] VALUES ('2', 'user_2', 'p_2.png', 'okname_2');
INSERT INTO [hess_opportunity].[user] VALUES ('3', 'user_3', 'p_3.png', 'okname_3');
INSERT INTO [hess_opportunity].[user] VALUES ('123456', 'testuser', 'p_4.png', '0oar95zt9zIpYuz6A0h7');
GO
COMMIT
GO


-- ----------------------------
--  Primary key structure for table discussion_post
-- ----------------------------
ALTER TABLE [hess_opportunity].[discussion_post] ADD
	CONSTRAINT [PK__discussi__3213E83FE7AA3CFC] PRIMARY KEY CLUSTERED ([id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table document
-- ----------------------------
ALTER TABLE [hess_opportunity].[document] ADD
	CONSTRAINT [PK__document__3213E83F0EA07775] PRIMARY KEY CLUSTERED ([id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table opportunity
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity] ADD
	CONSTRAINT [PK__opportun__3213E83FCE25135A] PRIMARY KEY CLUSTERED ([id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table opportunity_document
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity_document] ADD
	CONSTRAINT [PK__opportun__6013C20009318BA3] PRIMARY KEY CLUSTERED ([opportunity_id],[document_id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table opportunity_link
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity_link] ADD
	CONSTRAINT [PK__opportun__3213E83FF96ED495] PRIMARY KEY CLUSTERED ([id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table opportunity_members
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity_members] ADD
	CONSTRAINT [PK__opportun__52EE4FFAAA254315] PRIMARY KEY CLUSTERED ([opportunity_id],[user_id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table opportunity_phase
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity_phase] ADD
	CONSTRAINT [PK__opportun__3213E83FDCAE1D26] PRIMARY KEY CLUSTERED ([id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table opportunity_tag
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity_tag] ADD
	CONSTRAINT [PK__opportun__CD5CC6A1B4CC1630] PRIMARY KEY CLUSTERED ([opportunity_id],[tag_id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table opportunity_views
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity_views] ADD
	CONSTRAINT [PK__opportun__52EE4FFA8F0DFDD3] PRIMARY KEY CLUSTERED ([opportunity_id],[user_id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table source
-- ----------------------------
ALTER TABLE [hess_opportunity].[source] ADD
	CONSTRAINT [PK__source__3213E83FA9F3E7C6] PRIMARY KEY CLUSTERED ([id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table tag
-- ----------------------------
ALTER TABLE [hess_opportunity].[tag] ADD
	CONSTRAINT [PK__tag__3213E83F7568281E] PRIMARY KEY CLUSTERED ([id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table tech_theme
-- ----------------------------
ALTER TABLE [hess_opportunity].[tech_theme] ADD
	CONSTRAINT [PK__tech_the__3213E83FA3B7C9CA] PRIMARY KEY CLUSTERED ([id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Primary key structure for table user
-- ----------------------------
ALTER TABLE [hess_opportunity].[user] ADD
	CONSTRAINT [PK__user__3213E83FC880AB9A] PRIMARY KEY CLUSTERED ([id]) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [default]
GO

-- ----------------------------
--  Foreign keys structure for table discussion_post
-- ----------------------------
ALTER TABLE [hess_opportunity].[discussion_post] ADD
	CONSTRAINT [FK__discussio__oppor__47D32352] FOREIGN KEY ([opportunity_id]) REFERENCES [hess_opportunity].[opportunity] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT [FK__discussio__autho__48C7478B] FOREIGN KEY ([author_id]) REFERENCES [hess_opportunity].[user] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

-- ----------------------------
--  Foreign keys structure for table opportunity
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity] ADD
	CONSTRAINT [FK__opportuni__tech___430E6E35] FOREIGN KEY ([tech_theme_id]) REFERENCES [hess_opportunity].[tech_theme] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT [FK__opportuni__sourc__4402926E] FOREIGN KEY ([source_id]) REFERENCES [hess_opportunity].[source] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT [FK__opportuni__owner__44F6B6A7] FOREIGN KEY ([owner_id]) REFERENCES [hess_opportunity].[user] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

-- ----------------------------
--  Foreign keys structure for table opportunity_document
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity_document] ADD
	CONSTRAINT [FK__opportuni__oppor__4D8BFCA8] FOREIGN KEY ([opportunity_id]) REFERENCES [hess_opportunity].[opportunity] ([id]) ON DELETE CASCADE ON UPDATE NO ACTION,
	CONSTRAINT [FK__opportuni__docum__4E8020E1] FOREIGN KEY ([document_id]) REFERENCES [hess_opportunity].[document] ([id]) ON DELETE CASCADE ON UPDATE NO ACTION
GO

-- ----------------------------
--  Foreign keys structure for table opportunity_link
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity_link] ADD
	CONSTRAINT [FK__opportuni__oppor__515C8D8C] FOREIGN KEY ([opportunity_id]) REFERENCES [hess_opportunity].[opportunity] ([id]) ON DELETE CASCADE ON UPDATE NO ACTION
GO

-- ----------------------------
--  Foreign keys structure for table opportunity_members
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity_members] ADD
	CONSTRAINT [FK__opportuni__oppor__5438FA37] FOREIGN KEY ([opportunity_id]) REFERENCES [hess_opportunity].[opportunity] ([id]) ON DELETE CASCADE ON UPDATE NO ACTION,
	CONSTRAINT [FK__opportuni__user___552D1E70] FOREIGN KEY ([user_id]) REFERENCES [hess_opportunity].[user] ([id]) ON DELETE CASCADE ON UPDATE NO ACTION
GO

-- ----------------------------
--  Foreign keys structure for table opportunity_phase
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity_phase] ADD
	CONSTRAINT [FK__opportuni__oppor__58098B1B] FOREIGN KEY ([opportunity_id]) REFERENCES [hess_opportunity].[opportunity] ([id]) ON DELETE CASCADE ON UPDATE NO ACTION
GO

-- ----------------------------
--  Foreign keys structure for table opportunity_tag
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity_tag] ADD
	CONSTRAINT [FK__opportuni__oppor__5CCE4038] FOREIGN KEY ([opportunity_id]) REFERENCES [hess_opportunity].[opportunity] ([id]) ON DELETE CASCADE ON UPDATE NO ACTION,
	CONSTRAINT [FK__opportuni__tag_i__5DC26471] FOREIGN KEY ([tag_id]) REFERENCES [hess_opportunity].[tag] ([id]) ON DELETE CASCADE ON UPDATE NO ACTION
GO

-- ----------------------------
--  Foreign keys structure for table opportunity_views
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity_views] ADD
	CONSTRAINT [FK__opportuni__oppor__609ED11C] FOREIGN KEY ([opportunity_id]) REFERENCES [hess_opportunity].[opportunity] ([id]) ON DELETE CASCADE ON UPDATE NO ACTION,
	CONSTRAINT [FK__opportuni__user___6192F555] FOREIGN KEY ([user_id]) REFERENCES [hess_opportunity].[user] ([id]) ON DELETE CASCADE ON UPDATE NO ACTION
GO

-- ----------------------------
--  Options for table discussion_post
-- ----------------------------
ALTER TABLE [hess_opportunity].[discussion_post] SET (LOCK_ESCALATION = TABLE)
GO
DBCC CHECKIDENT ('[hess_opportunity].[discussion_post]', RESEED, 1)
GO

-- ----------------------------
--  Options for table document
-- ----------------------------
ALTER TABLE [hess_opportunity].[document] SET (LOCK_ESCALATION = TABLE)
GO

-- ----------------------------
--  Options for table opportunity
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity] SET (LOCK_ESCALATION = TABLE)
GO
DBCC CHECKIDENT ('[hess_opportunity].[opportunity]', RESEED, 1)
GO

-- ----------------------------
--  Options for table opportunity_document
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity_document] SET (LOCK_ESCALATION = TABLE)
GO

-- ----------------------------
--  Options for table opportunity_link
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity_link] SET (LOCK_ESCALATION = TABLE)
GO
DBCC CHECKIDENT ('[hess_opportunity].[opportunity_link]', RESEED, 1)
GO

-- ----------------------------
--  Options for table opportunity_members
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity_members] SET (LOCK_ESCALATION = TABLE)
GO

-- ----------------------------
--  Options for table opportunity_phase
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity_phase] SET (LOCK_ESCALATION = TABLE)
GO
DBCC CHECKIDENT ('[hess_opportunity].[opportunity_phase]', RESEED, 1)
GO

-- ----------------------------
--  Options for table opportunity_tag
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity_tag] SET (LOCK_ESCALATION = TABLE)
GO

-- ----------------------------
--  Options for table opportunity_views
-- ----------------------------
ALTER TABLE [hess_opportunity].[opportunity_views] SET (LOCK_ESCALATION = TABLE)
GO

-- ----------------------------
--  Options for table source
-- ----------------------------
ALTER TABLE [hess_opportunity].[source] SET (LOCK_ESCALATION = TABLE)
GO

-- ----------------------------
--  Options for table tag
-- ----------------------------
ALTER TABLE [hess_opportunity].[tag] SET (LOCK_ESCALATION = TABLE)
GO

-- ----------------------------
--  Options for table tech_theme
-- ----------------------------
ALTER TABLE [hess_opportunity].[tech_theme] SET (LOCK_ESCALATION = TABLE)
GO

-- ----------------------------
--  Options for table user
-- ----------------------------
ALTER TABLE [hess_opportunity].[user] SET (LOCK_ESCALATION = TABLE)
GO

