var mongoose = require('mongoose');
// Setup schema
var commentSchema = mongoose.Schema({
    username: {
        type: String,
        required: true
    },
    comment: {
        required: true,
        type: String
    },
    timeComment: {
        type : Date,
        default: Date.now
    },
    post : { type: Schema.Types.ObjectId, ref: 'Post' }

});
// Export post model
var Comment = module.exports = mongoose.model('post', commentSchema);
module.exports.get = function (callback, limit) {
    Comment.find(callback).limit(limit);
}