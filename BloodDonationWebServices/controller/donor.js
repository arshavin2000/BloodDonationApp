Donor = require('../models/donor');
// Handle index actions
exports.index = function (req, res) {
    Donor.get(function (err, donors) {
        if (err) {
            res.json({
                status: "error",
                message: err,
            });
        }
        res.json({
            status: "success",
            message: "donors retrieved successfully",
            data: donors
        });
    });
};
// Handle create post actions
exports.new = function (req, res) {
    var donor = new Donor();
    donor.id = req.body.id;
    donor.email = req.body.email;
    donor.number = req.body.number;
    donor.firstname = req.body.firstname;
    donor.lastname = req.body.lastname;
    donor.bloodgroop = req.body.bloodgroop;
    donor.gender = req.body.gender;

// save the post and check for errors
    donor.save(function (err) {
        // if (err)
        //     res.json(err);
        res.json({
            message: 'New donor created!',
            data: donor
        });
    });
};
