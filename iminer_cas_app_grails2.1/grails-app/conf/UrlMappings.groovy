import org.apache.shiro.authz.AuthorizationException

class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		"500"(view: '/unauthorized',exception: AuthorizationException)
		"500"(view:'/error')
	}
}
