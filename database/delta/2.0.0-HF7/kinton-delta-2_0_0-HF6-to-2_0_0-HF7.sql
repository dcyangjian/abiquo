-- Remove the stuff generated by the workaround for issue ABICLOUDPREMIUM-4422
DROP TABLE IF EXISTS ABICLOUDPREMIUM_4422_INITIAL_FIX_OUTPUT;
DROP PROCEDURE IF EXISTS ABICLOUDPREMIUM_4422_INITIAL_FIX;
DROP TRIGGER IF EXISTS ABICLOUDPREMIUM_4422_TRIGGER;