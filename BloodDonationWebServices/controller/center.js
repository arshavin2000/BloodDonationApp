const Center = require('../models/center');


// Handle index actions
exports.index = function (req, res) {
    Center.get(function (err, centers) {
        if (err) {
            res.json({
                status: "error",
                message: err,
            });
        }
        res.json({
                      data: centers
        });
    });
};


// Handle create post actions
exports.new = function (req, res) {
    var center = new Center({

      name : req.body.name,
      address : req.body.address,
      tel : req.body.tel,
      fax : req.body.fax,
      site : req.body.site


    });


console.log(center.name);
// save the post and check for errors
    center.save(function (err) {
        // if (err)
        //     res.json(err);
        res.json({
            message: 'New center created!',
        });
    });
};
