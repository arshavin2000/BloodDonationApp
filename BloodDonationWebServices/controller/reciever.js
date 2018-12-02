Reciever = require('../models/receiver');
// Handle index actions
exports.index = function (req, res) {
    Reciever.get(function (err, recievers) {
        if (err) {
            res.json({
                status: "error",
                message: err,
            });
        }
        res.json(
           recievers
        );
    });
};
// Handle create Reciever actions
exports.new = function (req, res) {
    var reciever = new Reciever();
    reciever.bloodgroup = req.body.recieverImage;
    reciever.recieverion = req.body.recieverText;
    reciever.number = req.body.recieverText;
  
// save the Reciever and check for errors
reciever.save(function (err) {

         if (err)
             res.json(err);
        res.json({
            message: 'New Reciever created!',
            data: reciever
        });
    });
};
// Handle view Reciever info
exports.view = function (req, res) {
    Reciever.findById(req.params.reciever_id, function (err, reciever) {
        if (err)
            res.send(err);
        res.json({
            message: 'Reciever details loading..',
            data: reciever
        });
    });
};
// Handle update Reciever info
exports.update = function (req, res) {
    Reciever.findById(req.params.reciever_id, function (err, reciever) {
            if (err)
                res.send(err);
            reciever.comments.push(req.body.comments);
            // save the Reciever and check for errors
                reciever.save(function (err) {
                    //if (err)
                      //  res.json(err);
                    res.json({
                        message: 'Reciever Info updated',
                        data: reciever
                    });
                });
            });
};
// Handle delete Reciever
exports.delete = function (req, res) {
    Reciever.remove({
        _id: req.params.reciever_id
    }, function (err, reciever) {
        if (err)
            res.send(err);
    });
}
