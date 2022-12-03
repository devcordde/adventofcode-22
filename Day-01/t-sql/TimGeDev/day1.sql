DROP TABLE IF EXISTS Tabelle1;
GO

CREATE TABLE [dbo].[Tabelle1](
	[F1] int NULL,
) ON [PRIMARY]
GO

BULK INSERT dbo.Tabelle1
FROM 'E:\input_day1.txt'
WITH
(
  ROWTERMINATOR  = '\n'
)
GO

ALTER TABLE Tabelle1
ADD [ID] [bigint] IDENTITY(1,1) NOT NULL
GO


WITH baseQuery as (
	SELECT TOP (3) SUM(F1) Kalorien
	FROM
		(
			SELECT *,
				(SELECT COALESCE(MAX(q2.ID),0) from Tabelle1 q2 WHERE F1 is NULL AND q2.ID < q1.ID) as preNull,
				(SELECT COALESCE(MIN(q2.ID),99999) from Tabelle1 q2 WHERE F1 is NULL AND q2.ID > q1.ID) as sufNull
			FROM Tabelle1 q1
			WHERE F1 IS NOT NULL
		) b
		GROUP BY b.preNull, b.sufNull
		ORDER BY Kalorien DESC
)
SELECT MAX(Kalorien) as Part1, SUM(Kalorien) as Part2 FROM baseQuery;
GO