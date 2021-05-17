const express = require('express');
const opportunityRouter = require('./opportunity');
const router = express.Router();

router.use('/', opportunityRouter);

module.exports = router;
