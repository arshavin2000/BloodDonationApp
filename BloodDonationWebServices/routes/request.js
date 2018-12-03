let router = require('express').Router();
// Import post controller
var requestController = require('../controller/request');
// post routes
router.route('/request')
    .post(requestController.new)


// Export API routes
module.exports = router;
