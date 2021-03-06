CREATE DATABASE [OPPORTUNITY]
GO
USE [OPPORTUNITY]
GO
/****** Object:  Schema [tracker]    Script Date: 5/18/2021 12:20:29 AM ******/
CREATE SCHEMA [tracker]
GO
/****** Object:  Table [tracker].[criteria]    Script Date: 5/18/2021 12:20:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [tracker].[criteria](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[question_text] [nvarchar](max) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [tracker].[discussion_post]    Script Date: 5/18/2021 12:20:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [tracker].[discussion_post](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[opportunity_id] [int] NULL,
	[author_id] [int] NOT NULL,
	[parent_id] [int] NULL,
	[content] [nvarchar](max) NOT NULL,
	[created_by] [int] NOT NULL,
	[updated_by] [int] NULL,
	[created_on] [datetimeoffset](7) NULL,
	[updated_on] [datetimeoffset](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [tracker].[document]    Script Date: 5/18/2021 12:20:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [tracker].[document](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](128) NOT NULL,
	[document_type] [nvarchar](32) NOT NULL,
	[location] [nvarchar](128) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [tracker].[evaluation]    Script Date: 5/18/2021 12:20:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [tracker].[evaluation](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[evaluation_notes] [nvarchar](max) NULL,
	[user_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [tracker].[evaluation_response]    Script Date: 5/18/2021 12:20:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [tracker].[evaluation_response](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[evaluation_id] [int] NOT NULL,
	[criteria_id] [int] NOT NULL,
	[score] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [tracker].[opportunity]    Script Date: 5/18/2021 12:20:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [tracker].[opportunity](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](256) NOT NULL,
	[tech_theme_id] [int] NOT NULL,
	[source_id] [int] NOT NULL,
	[description] [nvarchar](max) NOT NULL,
	[company] [nvarchar](128) NOT NULL,
	[owner_id] [int] NOT NULL,
	[created_by] [int] NOT NULL,
	[updated_by] [int] NULL,
	[created_on] [datetimeoffset](7) NULL,
	[updated_on] [datetimeoffset](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [tracker].[opportunity_document]    Script Date: 5/18/2021 12:20:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [tracker].[opportunity_document](
	[document_id] [int] NOT NULL,
	[opportunity_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[document_id] ASC,
	[opportunity_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [tracker].[opportunity_link]    Script Date: 5/18/2021 12:20:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [tracker].[opportunity_link](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[opportunity_id] [int] NOT NULL,
	[link] [nvarchar](256) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [tracker].[opportunity_members]    Script Date: 5/18/2021 12:20:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [tracker].[opportunity_members](
	[opportunity_id] [int] NOT NULL,
	[user_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[opportunity_id] ASC,
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [tracker].[opportunity_phase]    Script Date: 5/18/2021 12:20:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [tracker].[opportunity_phase](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[opportunity_id] [int] NOT NULL,
	[status] [nvarchar](128) NOT NULL,
	[start_date] [nvarchar](45) NOT NULL,
	[end_date] [nvarchar](45) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [tracker].[opportunity_tag]    Script Date: 5/18/2021 12:20:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [tracker].[opportunity_tag](
	[opportunity_id] [int] NOT NULL,
	[tag_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[opportunity_id] ASC,
	[tag_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [tracker].[opportunity_views]    Script Date: 5/18/2021 12:20:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [tracker].[opportunity_views](
	[opportunity_id] [int] NOT NULL,
	[user_id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[opportunity_id] ASC,
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [tracker].[source]    Script Date: 5/18/2021 12:20:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [tracker].[source](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](128) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [tracker].[tag]    Script Date: 5/18/2021 12:20:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [tracker].[tag](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[label] [nvarchar](128) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [tracker].[tech_theme]    Script Date: 5/18/2021 12:20:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [tracker].[tech_theme](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[icon] [nvarchar](128) NOT NULL,
	[name] [nvarchar](128) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [tracker].[user]    Script Date: 5/18/2021 12:20:29 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [tracker].[user](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](128) NOT NULL,
	[profile_picture] [nvarchar](128) NOT NULL,
	[okta_username] [nvarchar](45) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [tracker].[document] ON 

INSERT [tracker].[document] ([id], [name], [document_type], [location]) VALUES (1, N'Document-1', N'type-1', N'location-1')
INSERT [tracker].[document] ([id], [name], [document_type], [location]) VALUES (2, N'Document-2', N'type-2', N'location-2')
INSERT [tracker].[document] ([id], [name], [document_type], [location]) VALUES (3, N'Document-1', N'type-2', N'location-2')
SET IDENTITY_INSERT [tracker].[document] OFF
GO
SET IDENTITY_INSERT [tracker].[source] ON 

INSERT [tracker].[source] ([id], [name]) VALUES (1, N'Source-1')
INSERT [tracker].[source] ([id], [name]) VALUES (2, N'Source-2')
INSERT [tracker].[source] ([id], [name]) VALUES (3, N'Source-3')
INSERT [tracker].[source] ([id], [name]) VALUES (4, N'Source-4')
INSERT [tracker].[source] ([id], [name]) VALUES (5, N'Source-5')
INSERT [tracker].[source] ([id], [name]) VALUES (6, N'Source-6')
SET IDENTITY_INSERT [tracker].[source] OFF
GO
SET IDENTITY_INSERT [tracker].[tag] ON 

INSERT [tracker].[tag] ([id], [label]) VALUES (1, N'Tag-1')
INSERT [tracker].[tag] ([id], [label]) VALUES (2, N'Tag-2')
INSERT [tracker].[tag] ([id], [label]) VALUES (3, N'Tag-3')
INSERT [tracker].[tag] ([id], [label]) VALUES (4, N'Tag-4')
INSERT [tracker].[tag] ([id], [label]) VALUES (5, N'Tag-5')
INSERT [tracker].[tag] ([id], [label]) VALUES (6, N'Tag-6')
SET IDENTITY_INSERT [tracker].[tag] OFF
GO
SET IDENTITY_INSERT [tracker].[tech_theme] ON 

INSERT [tracker].[tech_theme] ([id], [icon], [name]) VALUES (1, N'Icon-1', N'Name-1')
INSERT [tracker].[tech_theme] ([id], [icon], [name]) VALUES (2, N'Icon-2', N'Name-2')
INSERT [tracker].[tech_theme] ([id], [icon], [name]) VALUES (3, N'Icon-3', N'Name-3')
INSERT [tracker].[tech_theme] ([id], [icon], [name]) VALUES (4, N'Icon-4', N'Name-4')
INSERT [tracker].[tech_theme] ([id], [icon], [name]) VALUES (5, N'Icon-5', N'Name-5')
INSERT [tracker].[tech_theme] ([id], [icon], [name]) VALUES (6, N'Icon-6', N'Name-6')
SET IDENTITY_INSERT [tracker].[tech_theme] OFF
GO
SET IDENTITY_INSERT [tracker].[user] ON 

INSERT [tracker].[user] ([id], [name], [profile_picture], [okta_username]) VALUES (1, N'emre', N'picture', N'emre.isbilir@gmail.com')
SET IDENTITY_INSERT [tracker].[user] OFF
GO
ALTER TABLE [tracker].[discussion_post]  WITH CHECK ADD FOREIGN KEY([author_id])
REFERENCES [tracker].[user] ([id])
GO
ALTER TABLE [tracker].[discussion_post]  WITH CHECK ADD FOREIGN KEY([opportunity_id])
REFERENCES [tracker].[opportunity] ([id])
ON DELETE SET NULL
GO
ALTER TABLE [tracker].[discussion_post]  WITH CHECK ADD FOREIGN KEY([parent_id])
REFERENCES [tracker].[discussion_post] ([id])
GO
ALTER TABLE [tracker].[evaluation]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [tracker].[user] ([id])
GO
ALTER TABLE [tracker].[evaluation_response]  WITH CHECK ADD FOREIGN KEY([criteria_id])
REFERENCES [tracker].[criteria] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [tracker].[evaluation_response]  WITH CHECK ADD FOREIGN KEY([evaluation_id])
REFERENCES [tracker].[evaluation] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [tracker].[opportunity]  WITH CHECK ADD FOREIGN KEY([owner_id])
REFERENCES [tracker].[user] ([id])
GO
ALTER TABLE [tracker].[opportunity]  WITH CHECK ADD FOREIGN KEY([source_id])
REFERENCES [tracker].[source] ([id])
GO
ALTER TABLE [tracker].[opportunity]  WITH CHECK ADD FOREIGN KEY([tech_theme_id])
REFERENCES [tracker].[tech_theme] ([id])
GO
ALTER TABLE [tracker].[opportunity_document]  WITH CHECK ADD FOREIGN KEY([document_id])
REFERENCES [tracker].[document] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [tracker].[opportunity_document]  WITH CHECK ADD FOREIGN KEY([opportunity_id])
REFERENCES [tracker].[opportunity] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [tracker].[opportunity_link]  WITH CHECK ADD FOREIGN KEY([opportunity_id])
REFERENCES [tracker].[opportunity] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [tracker].[opportunity_members]  WITH CHECK ADD FOREIGN KEY([opportunity_id])
REFERENCES [tracker].[opportunity] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [tracker].[opportunity_members]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [tracker].[user] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [tracker].[opportunity_phase]  WITH CHECK ADD FOREIGN KEY([opportunity_id])
REFERENCES [tracker].[opportunity] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [tracker].[opportunity_tag]  WITH CHECK ADD FOREIGN KEY([opportunity_id])
REFERENCES [tracker].[opportunity] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [tracker].[opportunity_tag]  WITH CHECK ADD FOREIGN KEY([tag_id])
REFERENCES [tracker].[tag] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [tracker].[opportunity_views]  WITH CHECK ADD FOREIGN KEY([opportunity_id])
REFERENCES [tracker].[opportunity] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [tracker].[opportunity_views]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [tracker].[user] ([id])
ON DELETE CASCADE
GO
