$(document).ready(function() {
    var lock = new Auth0Lock(AUTH0_CLIENT_ID, AUTH0_DOMAIN, {
    auth: {
      redirectUrl: AUTH0_CALLBACK_URL,
      responseType: 'code',
      params: {
        scope: 'openid email' // Learn about scopes: https://auth0.com/docs/scopes
      }
    }
  });

    $('#signIn').click(function(e) {
      e.preventDefault();
      lock.show();
    });

    $('#requestInvite').click(function(e){
      console.log("Request Invite " + e)
    })
});
