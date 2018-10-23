package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        name('get item')
        method('GET')
        url('/items/1')
        headers {
            header('Accept', 'application/json;charset=UTF-8')
        }
    }
    response {
        status 200
        headers {
            header('Content-Type', 'application/json;charset=UTF-8')
        }
        body([
                name: 'A',
                count: 100,
                price: 40.0
        ])
    }
}
