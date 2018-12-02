var mongoose = require('mongoose');
// Setup schema
var centerSchema = mongoose.Schema({
    name: {
        type: String,
    },
    address: {
        type: String,
    },
    tel: {
        type: String,
    },
    fax: {
        type: String,
    },
    site: {
        type: String,
    }
});
// Export post model
const Center = module.exports = mongoose.model('center', centerSchema);
module.exports.get = function (callback, limit) {
   Center.find(callback).limit(limit);
}
