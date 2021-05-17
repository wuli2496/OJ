const express = require('express');
const services = require('../services/opportunityService');

const router = express.Router();

router.get('/opportunities', services.query);

module.exports = router;
