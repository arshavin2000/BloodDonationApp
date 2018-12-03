let router = require('express').Router();
// Import post controller
var donorController = require('../controller/donor');
// post routes
router.route('/donor')
    .get(donorController.index)
    .post(donorController.new);
router.route('/donor/:id')
    .get(donorController.view)
    .patch(donorController.update);
router.route('/donor/email/:email')
    .get(donorController.email)
router.route('/donor/number/:number')
    .get(donorController.number)



// Export API routes
module.exports = router;
