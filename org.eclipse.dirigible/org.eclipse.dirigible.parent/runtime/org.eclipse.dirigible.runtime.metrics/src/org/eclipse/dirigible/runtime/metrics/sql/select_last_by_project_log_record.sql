SELECT ACCLOG_PROJECT, ACCLOG_PERIOD, COUNT(*) AS ACCLOG_COUNT 
FROM DGB_ACCESS_LOG 
WHERE ACCLOG_PERIOD > ? 
GROUP BY ACCLOG_PROJECT, ACCLOG_PERIOD 
ORDER BY ACCLOG_PROJECT, ACCLOG_PERIOD