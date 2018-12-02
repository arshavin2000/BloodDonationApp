var mongoose = require('mongoose');
// Setup schema
var receiverSchema = mongoose.Schema({
    bloodgroup: {
        type: String,
    },
    postion: {
        type: String,
    },
    number: {
        type: String,
    },
    timePost: {
        type : Date,
        default: Date.now
    },
});
// Export post model
const Receiver = module.exports = mongoose.model('receiver', receiverSchema);
module.exports.get = function (callback, limit) {
    Receiver.find(callback).limit(limit);
}
