let router = require('express').Router();
// Import post controller
var centerController = require('../controller/center');
// post routes
router.route('/centers')
    .get(centerController.index)
    .post(centerController.new);



// Export API routes
module.exports = router;
