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
                copyItemTooltip = group "copy_item_tooltip" {
                    hoveredInvslotFinder = component "HoveredInvslotFinder" {
                        this -> moddingAPI "Uses"
                    }

                    getItemTooltipHandler = component "GetItemTooltipHandler" {
                        tag "Core"

                        this -> hoveredInvslotFinder "Uses"
                    }

                    copyHoveredItemTooltipListener = component "CopyHoveredItemTooltipListener" {
                        this -> getItemTooltipHandler "Uses"
                        moddingAPI -> this "Triggers"
                    }
                }

                copySkullId = group "copy_skull_id" {
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
                        moddingApi -> this "Triggers"
                    }
                }

                viewItemId = group "view_item_id" {
                    hoveredItemIdFinder = component "HoveredItemIdFinder" {
                        this -> moddingAPI "Uses"
                    }

                    getItemIdHandler = component "GetItemIdHandler" {
                        tag "Core"

                        this -> hoveredItemIdFinder "Uses"
                    }

                    tooltipItemIdAppender = component "TooltipItemIdAppender" {
                        this -> getItemIdHandler "Uses"
                        moddingAPI -> this "Triggers"
                    }
                }

                copyDataTags = group "copy_data_tags" {
                    hoveredItemDataTagsFinder = component "HoveredItemDataTagsFinder" {
                        this -> moddingAPI "Uses"
                    }

                    facingEntityDataTagsFinder = component "FacingEntityDataTagsFinder" {
                        this -> moddingAPI "Uses"
                    }

                    getDataTagsHandler = component "GetDataTagsHandler" {
                        tag "Core"

                        this -> hoveredItemDataTagsFinder "Uses"
                        this -> facingEntityDataTagsFinder "Uses"
                    }

                    copyDataTagsListener = component "CopyDataTagsListener" {
                        this -> getDataTagsHandler "Uses"
                        moddingAPI -> this "Triggers"
                    }
                }

                copyOpenedUi = group "copy_opened_ui" {
                    openedChestContainerFinder = component "OpenedChestContainerFinder" {
                        this -> moddingAPI "Uses"
                    }

                    getOpenedUiHandler = component "GetOpenedUiHandler" {
                        tag "Core"

                        this -> openedChestContainerFinder "Uses"
                    }

                    copyOpenedUiListener = component "CopyOpenedUiListener" {
                        this -> getOpenedUiHandler "Uses"
                        moddingAPI -> this "Triggers"
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
