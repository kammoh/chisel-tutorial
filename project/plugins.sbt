resolvers := Seq(
  Resolver.url("kamyar-maven", url("https://nexus.kamyar.xyz/repository/maven-public/")),
  Resolver.url("kamyar-ivy", url("https://nexus.kamyar.xyz/repository/ivy-public/"))(Resolver.ivyStylePatterns)
  ) ++: resolvers.value

fullResolvers := {
  Seq(
    Resolver.url("kamyar-maven", url("https://nexus.kamyar.xyz/repository/maven-public/")),
    Resolver.url("kamyar-ivy", url("https://nexus.kamyar.xyz/repository/ivy-public/"))(Resolver.ivyStylePatterns)
  )  ++: fullResolvers.value
}
