const Request = require('../models/request');


exports.new = function (req, res) {
    var request = new Request({

      bloodgroup : req.body.bloodgroup,
      place : req.body.place,
      donor : req.body.donor,


    });


console.log(request.place);
// save the post and check for errors
request.save(function (err) {

     if (err)
         res.json(err);
    res.json({
        data: request,
    });
});
};
exports.index = function (req, res) {
    Request.get(function (err, posts) {
        if (err) {
            res.json({
                status: "error",
                message: err,
            });
        }
        res.json(
posts
            );
    });
};
