let router = require('express').Router();
// Import post controller
var postController = require('../controller/post');
// post routes
router.route('/posts')
    .get(postController.index)
    .post(postController.new);
router.route('/posts/:post_id')
    .get(postController.view)
    .patch(postController.update)
    .put(postController.update)
    .delete(postController.delete);

// Export API routes
module.exports = router;