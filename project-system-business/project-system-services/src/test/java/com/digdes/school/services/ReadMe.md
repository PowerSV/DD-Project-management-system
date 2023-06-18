Не получилось сделать тест внутри модуля, поэтому пришлось перенести тесты
в модуль APP. Ошибка была связана с тем, что не получалось получить класс
MemberJpaRepository
\
\
\
org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'memberServiceImpl': Unsatisfied dependency expressed through constructor parameter 1: No qualifying bean of type 'com.digdes.school.repos.JpaRepos.MemberJpaRepository' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {}
2023-06-16T23:53:29.442+03:00 ERROR 15312 --- [           main] o.s.b.d.LoggingFailureAnalysisReporter   :

***************************
APPLICATION FAILED TO START
***************************

Description:

Parameter 1 of constructor in com.digdes.school.services.impl.MemberServiceImpl required a bean of type 'com.digdes.school.repos.JpaRepos.MemberJpaRepository' that could not be found.