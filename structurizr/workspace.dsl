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
                        this -> moddingAPI "Registers event on"
                    }
                }

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
                        this -> moddingAPI "Registers event on"
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
                        this -> moddingAPI "Registers event on"
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
                        this -> moddingAPI "Registers event on"
                    }
                }

                modUpdateCheckerGroup = group "mod_update_checker" {
                    githubLatestReleaseFinder = component "GitHubLatestReleaseFinder" {
                        this -> moddingAPI "Uses"
                    }

                    getNewVersionHandler = component "GetNewVersionHandler" {
                        tag "Core"

                        this -> githubLatestReleaseFinder "Uses"
                    }

                    modUpdateChecker = component "ModUpdateChecker" {
                        this -> getNewVersionHandler "Uses"
                        this -> moddingAPI "Registers event on"
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
                        this -> moddingAPI "Registers event on"
                    }
                }
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
