const Donor = require('../models/donor');


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
    var donor = new Donor({

      id : req.body.id,
      email : req.body.email,
      number : req.body.number,
      firstname : req.body.firstname,
      lastname : req.body.lastname,
      bloodgroup : req.body.bloodgroup,
      gender : req.body.gender

    });


console.log(donor.firstname);
// save the post and check for errors
    donor.save(function (err) {
        // if (err)
        //     res.json(err);
        res.json({
            message: 'New donor created!',
        });
    });
};


// Handle view post info
exports.view = function (req, res) {
  var query = { id: req.params.id };

    Donor.findOne(query, function (err, donor) {
        if (err)
            res.send(err);
            if(donor === null)
            {
        res.json({
            message: 'Donor details loading..',
            data: false
        });
      }
      else {
        res.json({
            message: 'Donor details loading..',
            data: true
        });
      }


    });
};


// Handle view post info
exports.email = function (req, res) {
  var query = { email: req.params.email };

    Donor.findOne(query, function (err, donor) {
        if (err)
            res.send(err);
            if(donor === null)
            {
        res.json({
            message: 'Donor details loading..',
            data: false
        });
      }
      else {
        res.json({
            message: 'Donor details loading..',
            data: true
        });
      }


    });
};

exports.number = function (req, res) {
  var query = { number: req.params.number };

    Donor.findOne(query, function (err, donor) {
        if (err)
            res.send(err);
            if(donor === null)
            {
        res.json({
            message: 'Donor details loading..',
            data: false
        });
      }
      else {
        res.json({
            message: 'Donor details loading..',
            data: true
        });
      }


    });
};
