package za.co.absa.spline.gateway.rest

import java.util

import org.springframework.context.annotation.{ComponentScan, Configuration}
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.servlet.config.annotation.{EnableWebMvc, WebMvcConfigurer}
import za.co.absa.spline.common.webmvc.{ScalaFutureMethodReturnValueHandler, UnitMethodReturnValueHandler}
import za.co.absa.spline.gateway.rest.messageconverter.Json4sHttpMessageConverter

import scala.concurrent.ExecutionContext.Implicits.global

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = Array(classOf[controller._package]))
class RESTConfig extends WebMvcConfigurer {

  override def extendMessageConverters(converters: util.List[HttpMessageConverter[_]]): Unit = {
    converters.add(new Json4sHttpMessageConverter)
  }

  override def addReturnValueHandlers(returnValueHandlers: util.List[HandlerMethodReturnValueHandler]): Unit = {
    returnValueHandlers.add(new UnitMethodReturnValueHandler)
    returnValueHandlers.add(new ScalaFutureMethodReturnValueHandler(
      minEstimatedTimeout = AppConfig.AdaptiveTimeout.min,
      durationToleranceFactor = AppConfig.AdaptiveTimeout.durationFactor
    ))
  }


}
