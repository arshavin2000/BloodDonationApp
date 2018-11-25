let router = require('express').Router();
// Import post controller
var donorController = require('../controller/donor');
// post routes
router.route('/donors')
    .get(donorController.index)
    .post(donorController.new);
router.route('/donors/:id')
    .get(donorController.view)
router.route('/donors/email/:email')
    .get(donorController.email)
router.route('/donors/number/:number')
    .get(donorController.number)



// Export API routes
module.exports = router;
