Request = require('../models/request');

const PushNotifications = require('@pusher/push-notifications-server');
 
let pushNotifications = new PushNotifications({
  instanceId: 'ab4bd9cb-feeb-44e0-bc22-aca7f7bcce78',
  secretKey: '9A2BB58EF6AFF679DCBED61E2ADC3FCC3A81D404A9F4B417669FBA5EE2B26BCD'
});
exports.new = function (req, res) {
  console.log(req.body);
  var request = new Request();
  request.bloodgroup = req.body.bloodgroup;
  request.place = req.body.place;
  request.donor = req.body.donor;
  
pushNotifications.publish([req.params.req_id], {
    apns: {
      aps: {
        alert: req.params.req_id
      }
    },
    fcm: {
      notification: {
        title: request.donor.firstname+" "+ request.donor.firstname,
        body: 'Ask for help'
      }
    }
  }).then((publishResponse) => {
    console.log('Just published:', publishResponse.publishId);
  }).catch((error) => {
    console.log('Error:', error);
  });
}