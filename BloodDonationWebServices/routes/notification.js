let router = require('express').Router();
// Import post controller
var notifController = require('../controller/notification');
// post routes
router.route('/notification/:req_id/:req_firstname/:req_lastname/:req_blood')
    .post(notifController.new);


// Export API routes
module.exports = router;