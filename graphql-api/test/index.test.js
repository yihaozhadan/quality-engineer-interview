/**
 * Test a public GraphQL API
 * @see https://studio.apollographql.com/public/countries/variant/current/home
 */
const expect = require('chai').expect;

describe('Country API', () => {
    const url = 'https://countries.trevorblades.com';
    const query = `query Query {
  country(code: "US") {
    name
    native
    capital
    emoji
    currency
    languages {
      code
      name
    }
  }
}`;
    it('Country name is United States', async () => {
        const response = await fetch(url + '/graphql', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
            },
            body: JSON.stringify({
                query
            })
        });
        expect(response.status).to.equal(200);
        const data = await response.json();
        expect(data.data.country.name).to.equal('United States');
    });

    // TODO: Pass parameters to the query

    // TODO: Import countries.js and test all countries
});