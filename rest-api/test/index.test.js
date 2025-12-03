const expect = require('chai').expect;
const host = 'https://httpbin.org';

describe('Status codes ', async function() {
  it('return HTTP status 201', async function() {
    const response = await fetch(host + '/status/201');
    expect(response.status).to.equal(201);
  })

  it('return HTTP status 200', async function() {
    const dataToSend = {
      username: 'testuser',
      email: 'test@example.com'
    };
    const response = await fetch(host + '/status/200', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(dataToSend)
    });
    expect(response.status).to.equal(200);
  })
})

describe('Response formats ', async function() {
  // TODO: implement tests
})