workspace "WikiTools" {

    !identifiers hierarchical

    model {
        user = person "User"

        moddingAPI = softwareSystem "Minecraft & Modding API" {
            tags "External"

            user -> this "Uses"
        }

        wikitoolsSystem = softwareSystem "WikiTools System" {
            wikitools = container "WikiTools" {
                dataAccess = group "Data Access" {
                    hoveredInvslotFinder = component "HoveredInvslotFinder" {
                        this -> moddingAPI "Uses"
                    }
                }

                application = group "Application (WikiTools Core)" {
                    hoveredItemTooltipAccessor = component "HoveredItemTooltipAccessor" {
                        tag "Core"

                        this -> hoveredInvslotFinder "Uses"
                    }
                }

                presentation = group "Presentation" {
                    copyHoveredItemTooltipListener = component "CopyHoveredItemTooltipListener" {
                        this -> hoveredItemTooltipAccessor "Uses"
                        moddingAPI -> this "Uses"
                    }
                }
            }

            wikitoolsRenders = container "WikiTools Renders" {
                this -> moddingAPI "Uses"
                moddingAPI -> this "Uses"
            }
        }
    }

    views {
        systemContext wikitoolsSystem {
            include *
            include user
            autoLayout lr
        }

        container wikitoolsSystem {
            include *
            autoLayout lr
        }

        component wikitoolsSystem.wikitools {
            include *
            autoLayout lr
        }

        styles {
            element "Element" {
                background DarkGoldenRod
                color white
            }

            element "Person" {
                shape Person
            }

            element "External" {
                background Gray
                color white
            }

            element "Core" {
                background DarkCyan
                color white
            }
        }

        properties {
            "structurizr.timezone" "UTC"
        }
    }
}
