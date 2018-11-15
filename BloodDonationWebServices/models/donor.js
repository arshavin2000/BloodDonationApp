var mongoose = require('mongoose');
// Setup schema
var donorSchema = mongoose.Schema({
    id: {
        type: String,
        required: true
    },
    email: {
        type: String,
        required: true
    },
    number: {
        type: String,
        required: true
    },
    firstname: {
        type: String,
        required: true
    },
    lastname: {
        type: String,
        required: true
    },
    bloodgroop: {
        type: String,
        required: true
    },
    gender: {
        type: String,
        required: true
    }
});
// Export post model
var Donor = module.exports = mongoose.model('donor', donorSchema);
//module.exports.get = function (callback, limit) {
  //  Donor.find(callback).limit(limit);
//}
