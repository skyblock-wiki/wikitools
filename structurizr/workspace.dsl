workspace "WikiTools" {

    !identifiers hierarchical

    model {
        user = person "User"

        wikiSystem = softwareSystem "Wiki System" {
            tags "External"

            user -> this "Uses"
        }

        wikitoolsSystem = softwareSystem "WikiTools System" {
            wikitools = container "WikiTools" {
                copyHoveredItemTooltipListener = component "CopyHoveredItemTooltipListener"

                wikitoolsCore = group "WikiTools Core" {
                    getHoveredItemTooltipService = component "GetHoveredItemTooltipService" {
                        tags "Core"

                        copyHoveredItemTooltipListener -> this "Uses"
                    }
                }

                findHoveredItemFromMC = component "FindHoveredItemFromMC" {
                    getHoveredItemTooltipService -> this "Uses"
                }

                user -> this "Uses"
            }

            wikitoolsRenders = container "WikiTools Renders" {
                user -> this "Uses"
            }

            this -> wikiSystem "Gets data from"
        }
    }

    views {
        systemContext wikitoolsSystem {
            include *
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
