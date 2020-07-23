package com.trex;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.trex");

        noClasses()
            .that()
                .resideInAnyPackage("com.trex.service..")
            .or()
                .resideInAnyPackage("com.trex.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.trex.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
