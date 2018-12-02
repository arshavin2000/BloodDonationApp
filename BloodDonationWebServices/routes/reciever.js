let router = require('express').Router();
// Import reciever controller
var recieverController = require('../controller/reciever');
// reciever routes
router.route('/recievers')
    .get(recieverController.index)
    .post(recieverController.new);
router.route('/recievers/:reciever_id')
    .get(recieverController.view)
    .patch(recieverController.update)
    .put(recieverController.update)
    .delete(recieverController.delete);

// Export API routes
module.exports = router;