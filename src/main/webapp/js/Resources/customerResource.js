angular.module('demoApp').factory('CustomersResource', function($resource) {
	return $resource('rest/customers/:id', { id: '@_id' }, {
		update: {
		      method: 'PUT' // this method issues a PUT request
		    }
	});
});