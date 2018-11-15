Comment = require('../models/comment');
// Handle index actions
exports.findByCompanyId = (req, res) => {
	Comment.find({ company : req.params.postID })
	.exec(function (err, comments) {
		if (err){
			if(err.kind === 'ObjectId') {
				return res.status(404).send({
					message: "No comments with given Company Id " + req.params.postID
				});
			}
			return res.status(500).send({
				message: "Error retrieving Comments with given Post Id " + req.params.postID
			});
		}
		res.send(comments);
	});
};
// Handle create post actions
exports.new = function (req, res) {
    var comment = new Comment();
    comment.comment = req.body.comment;
    comment.username = req.body.username;
    comment.timeComment = req.body.timeComment;
    comment.timeComment = req.body.timeComment;
// save the post and check for errors
    post.save(req.params.post_id,function (err) {
        // if (err)
        //     res.json(err);
        res.json({
            message: 'New post created!',
            data: post
        });
    });
};
// Handle view post info
exports.view = function (req, res) {
    Post.findById(req.params.post_id, function (err, post) {
        if (err)
            res.send(err);
        res.json({
            message: 'post details loading..',
            data: post
        });
    });
};
// Handle update post info
exports.update = function (req, res) {
    Post.findById(req.params.post_id, function (err, post) {
            if (err)
                res.send(err);
   
            post.Comments.push( req.body.numberComments);
            post.co
            // save the post and check for errors
                post.save(function (err) {
                    //if (err)
                      //  res.json(err);
                    res.json({
                        message: 'post Info updated',
                        data: post
                    });
                });
            });
};
// Handle delete post
exports.delete = function (req, res) {
    Post.remove({
        _id: req.params.post_id
    }, function (err, post) {
        if (err)
            res.send(err);
    });
}
