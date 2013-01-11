package codestory2013

import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import com.excilys.ebi.gatling.jdbc.Predef._
import com.excilys.ebi.gatling.http.Headers.Names._
import akka.util.duration._
import bootstrap._

class Step1Simulation extends Simulation {

	val httpConf = httpConfig
		.baseURL("http://codestory.xhan.in:8086")
		.acceptCharsetHeader("ISO-8859-1,utf-8;q=0.7,*;q=0.7")
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
		.disableFollowRedirect

	val scn = scenario("Step 1").repeat(25) {
		exec(
			http("request_1")
				.get("/")
				.queryParam("q", "Es tu heureux de participer(OUI/NON)")
				.check(status.is(200)))
        }

        setUp(scn.users(20).ramp(10).protocolConfig(httpConf))

}
