
DROP TABLE IF EXISTS public.train;

CREATE TABLE public.trains (
	id serial NOT NULL,
	"name" varchar(100) NOT NULL,
	description varchar(1000) NULL,
	"distance-between-stop" varchar(100) NULL,
	"max-speed" varchar(50) NULL,
	"sharing-tracks" bool NULL,
	"grade-crossing" bool NULL,
	"train-frequency" varchar(100) NULL,
	amenities varchar(500) NULL,
	CONSTRAINT train_pkey PRIMARY KEY (id)
);
