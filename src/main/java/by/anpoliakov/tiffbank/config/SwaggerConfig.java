package by.anpoliakov.tiffbank.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Tiff Bank",
                description = "Banking REST service, with great functionality, was developed as a pet-project",
                version = "1.0.0",
                contact = @Contact(
                        name = "Andrew Poliakov",
                        email = "it.anpoliakov@gmail.com",
                        url = "https://github.com/anpoliakov/anpoliakov"
                )
        )
)
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {}