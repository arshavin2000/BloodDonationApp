let router = require('express').Router();
// Import post controller
var requestController = require('../controller/request');
// post routes
router.route('/request')
    .post(requestController.new)
    .get(requestController.index)


// Export API routes
module.exports = router;
