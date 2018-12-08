var mongoose = require('mongoose');
// Setup schema
var postSchema = mongoose.Schema({
    postImage: {
        type: String,
        required: true
    },
    type:{
        type : Number,
        default : 1
    },
    user: {
       
        
        firstname: {
            type: String,
        },
        lastname: {
            type: String,
        },
       
        url: {
          type: String,
        }
        
    }
    ,
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
    comments : [{
        textComment:String,
        time:String,
        username:String
        }]
});
// Export post model
var Post = module.exports = mongoose.model('post', postSchema);
module.exports.get = function (callback, limit) {
    Post.find(callback).limit(limit);
}