let router = require('express').Router();
// Import post controller
var donorController = require('../controller/donor');
// post routes
router.route('/donors')
    .get(donorController.index)
    .post(donorController.new);


// Export API routes
module.exports = router;
