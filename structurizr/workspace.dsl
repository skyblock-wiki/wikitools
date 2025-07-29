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
                getItemTooltip = group "get-item-tooltip" {
                    hoveredInvslotFinder = component "HoveredInvslotFinder" {
                        this -> moddingAPI "Uses"
                    }

                    getItemTooltipHandler = component "GetItemTooltipHandler" {
                        tag "Core"

                        this -> hoveredInvslotFinder "Uses"
                    }

                    copyHoveredItemTooltipListener = component "CopyHoveredItemTooltipListener" {
                        this -> getItemTooltipHandler "Uses"
                        moddingAPI -> this "Uses"
                    }
                }

                getSkullId = group "get-skull-id" {
                    hoveredSkullItemFinder = component "HoveredSkullItemFinder" {
                        this -> moddingAPI "Uses"
                    }

                    facingEntitySkullFinder = component "FacingEntitySkullFinder" {
                        this -> moddingAPI "Uses"
                    }

                    facingBlockSkullFinder = component "FacingBlockSkullFinder" {
                        this -> moddingAPI "Uses"
                    }

                    getSkullIdHandler = component "GetSkullIdHandler" {
                        tag "Core"

                        this -> hoveredSkullItemFinder "Uses"
                        this -> facingEntitySkullFinder "Uses"
                        this -> facingBlockSkullFinder "Uses"
                    }

                    copySkullIdListener = component "CopySkullIdListener" {
                        this -> getSkullIdHandler "Uses"
                        moddingApi -> this "Uses"
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
