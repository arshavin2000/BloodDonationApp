var mongoose = require('mongoose');
// Setup schema
var donorSchema = mongoose.Schema({
    id: {
        type: String,
    },
    email: {
        type: String,
    },
    number: {
        type: String,
    },
    firstname: {
        type: String,
    },
    lastname: {
        type: String,
    },
    bloodgroup: {
        type: String,
    },
    url: {
      type: String,
    },
    gender: {
        type: String,
    },

});
// Export post model
const Donor = module.exports = mongoose.model('donor', donorSchema);
module.exports.get = function (callback, limit) {
   Donor.find(callback).limit(limit);
}
