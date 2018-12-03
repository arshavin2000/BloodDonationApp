var mongoose = require('mongoose');
// Setup schema
var requestSchema = mongoose.Schema({
    bloodgroup: {
        type: String,
    },
    place: {
        type: String,
    },
    donor: {
      id : String,
      email : String,
      number : String,
      firstname : String,
      lastname : String,
      url : String,
      bloodgroup : String,
      gender : String,
    },


});
// Export post model
const Request = module.exports = mongoose.model('request', requestSchema);
module.exports.get = function (callback, limit) {
   Request.find(callback).limit(limit);
}
