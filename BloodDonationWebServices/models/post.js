var mongoose = require('mongoose');
// Setup schema
var postSchema = mongoose.Schema({
    postImage: {
        type: String,
        required: true
    },
    userName: {
        type: String,
    },
    postText: {
        type: String,
        required: true
    },
    timePost: {
        type : Date,
        default: Date.now
    },
    numberLikes: {
        type: Number,
        default: 0
    },
    NumberComments: {
        type: Number,
        default: 0
    },
    comments : []
});
// Export post model
var Post = module.exports = mongoose.model('post', postSchema);
module.exports.get = function (callback, limit) {
    Post.find(callback).limit(limit);
}