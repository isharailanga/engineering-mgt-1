import React, { Component, Children, version } from 'react';

import { MuiThemeProvider, withStyles} from '@material-ui/core/styles';

import { FormControl, InputLabel, Select, MenuItem, FilledInput, Tooltip, createMuiTheme } from '@material-ui/core';

import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import axios from 'axios';

const hostUrl = "https://" + window.location.host + window.contextPath + "/apis/checklist";


const styles = theme => ({
    root: {
        paddingBottom : '70px'
    }    
});

const PageWrapper_style = {
    padding : '30px',
    background : 'transparent',
    boxShadow : 'none'
};

const SelectDiv_style = {
    overflowX: "auto",
    padding : '20px'
};

const FormControl_style = {
    width : '47%',
    display : 'flex',
    wrap : 'nowrap',
    float : 'Left',
    margin : '10px'
};

const TableDiv_style = {
    overflowX: "auto",
};

const TableHeadCell_style = {
    border : '1px solid rgba(0, 0, 0, 0.1)',
    padding : '5px 7px',
    borderRadius : '3px',
    fontSize : '14px',
    fontWeight : 'normal',
    outline : 'none',
    textAlign : 'center'
};

const TableBorder_style = {
    border: "2px solid #aaa",
    overflow: "auto"
};

class HelloWorld extends Component {
    
    constructor(props) {
        super(props);
        this.state = {
            selected_ProductName: '',
            productNameList: [],
            selected_ProductVersion: '',
            productVersionList: []
        };

        this.handleChange = event => {
            this.setState({ [event.target.name]: event.target.value });
        };

        this.handleChange_ProductName = event => {
            this.setState ({ [event.target.name] : event.target.value });
            this.setState({
                selected_ProductName : event.target.value
            }, function () {
                console.log("The selected product name is : " + this.state.selected_ProductName);
            });   
        };

        this.handleChange_ProductVersion = event => {
            this.setState ({ [event.target.name] : event.target.value });
        }
    }

    // componentDidMount() {
    //     fetch("https://"+window.location.host+window.contextPath+"/apis/checklist/productnames")
    //     .then(
    //         (response) => {
    //             return response.json();
    //         }
    //     )
    //     .then(
    //         data => {
                
    //             let productsFromApi = data.products.map(products => { return {label : products, value : products} });
    //             this.setState({ productNameList : productsFromApi });
    //         } 
    //     )
    //     .catch(error => {
    //         console.log(error);
    //     });
    // }
    
    componentDidMount() {
        const getProductNamesURL = hostUrl + '/productNames';
        axios.create({
            withCredentials:false,
        })
        .get(getProductNamesURL)
        .then(
            res => {
                var response = res.data;
                console.log(response);

                let productsFromApi = response.products.map(products => {
                    return {productName : products}
                });

                this.setState({productNameList : productsFromApi }, function () {
                    console.log(this.state.productNameList)
                });
            })
        .catch(error => {
            console.log(error)
        });           
         
    } //End of componentDidMount

    componentDidUpdate(prevProps, prevState) {
        if(this.state.selected_ProductName !== prevState.selected_ProductName) {
            console.log("Product Name has changed")
            const getVersionsURL = hostUrl + '/versions?productName=' + this.state.selected_ProductName;
            axios.create({
                withCredentials : false,
            })
            .get(getVersionsURL)
            .then( 
                res => {
                    var response = res.data;
                    console.log(response);

                    let versionsFromApi = response.versions.map(
                        versions => {
                            return { versionTitle : versions.title, versionNumber : versions.number }
                        }
                    );

                    this.setState({productVersionList : versionsFromApi.map(
                        versionsFromApi => ({
                            versionTitle : versionsFromApi.versionTitle,
                            versionNumber : versionsFromApi.versionNumber
                        }))
                    }, function () {
                        console.log(this.state.productVersionList);
                    })
                }

            )
            .catch(error => {
                console.log(error);
            });
        }

    }

    render() {
        let reactTheme = createMuiTheme({
            palette : {
                type : this.props.muiTheme.name,
            },
            typography : {
                useNextVariants : true,
            },
        });


        return (
            <MuiThemeProvider theme = {reactTheme}>
                <div style = {PageWrapper_style}>

                    {/* Heading Div */}
                    <div>
                        <h2><center> Release Readiness Metrics </center></h2>
                    </div>

                    {/* Select Div */}
                    <div style = {SelectDiv_style}>
                        {/* Product Name Select */}
                        <FormControl style = {FormControl_style}>
                            <InputLabel htmlFor = "product-name"> Product Name</InputLabel>
                            <Select
                                value = { this.state.selected_ProductName }
                                onChange = { this.handleChange_ProductName }
                                inputProps = {{ 
                                    name : 'selected_ProductName',
                                    id : 'product-name' 
                                }}
                            >
                                {this.state.productNameList.map(
                                    (product) => <MenuItem value = {product.productName}> {product.productName} </MenuItem> 
                                )}
            
                            </Select>
                        </FormControl>

                        {/* Product Version Select */}
                        <FormControl style = {FormControl_style}>
                            <InputLabel htmlFor = "product-version"> Product Version </InputLabel>
                            <Select
                                value = { this.state.selected_ProductVersion }
                                onChange = { this.handleChange_ProductVersion }
                                inputProps = {{
                                    name : 'selected_ProductVersion',
                                    id : 'product-version'
                                }}
                            >
                                {this.state.productVersionList.map(
                                    (version) => <MenuItem value = {version.versionNumber}> {version.versionTitle} </MenuItem>
                                )}

                            </Select>
                        </FormControl>
                    </div>


                    {/* Table Div */}
                    <div style = {TableDiv_style}>
                        <Table style = {TableBorder_style}>
                            <colgroup>
                                <col style={{width: '10%'}}/>
                                <col style={{width: '60%'}}/>
                                <col style={{width: '30%'}}/>
                            </colgroup>
                        
                            <TableHead>
                                <TableCell> <h3> Status </h3> </TableCell>
                                <TableCell align = "center"> <h3> Metrics </h3> </TableCell>
                                <TableCell> <h3> Progress </h3> </TableCell>
                            </TableHead>
                        
                            <TableBody>
                                <TableRow>
                                    <TableCell> <b> GREEN </b> </TableCell>
                                    <TableCell align = "center">
                                        <Tooltip title = "Shows the performance results" placement = "top">
                                            <p>Security Scan Reports</p>
                                        </Tooltip>
                                    </TableCell>
                                    <TableCell>
                                        <a href = "Link" > 3 open </a>/ 4 Total
                                    </TableCell>
                                </TableRow>

                                <TableRow>
                                    <TableCell> <b> RED </b> </TableCell>
                                    <TableCell align = "center">
                                        <Tooltip title = "Shows the performance results" placement = "top">
                                            <p>Performance Results</p>
                                        </Tooltip>
                                    </TableCell>
                                    <TableCell>
                                        <a href = "Link" > 6 open </a>/ 10 Total
                                    </TableCell>
                                </TableRow>

                                <TableRow>
                                    <TableCell> <b> GREEN </b> </TableCell>
                                    <TableCell align = "center">
                                        <Tooltip title = "Shows the Customer Commitments" placement = "top">
                                            <p>Customer Commitments</p>
                                        </Tooltip>
                                    </TableCell>
                                    <TableCell>
                                        <a href = "Link" > 3 open </a>/ 4 Total
                                    </TableCell>
                                </TableRow>

                                <TableRow>
                                    <TableCell> <b> GREEN </b> </TableCell>
                                    <TableCell align = "center">
                                            <p>L1 Issues</p>
                                    </TableCell>
                                    <TableCell>
                                        <a href = "Link" > 3 </a>
                                    </TableCell>
                                </TableRow>

                                <TableRow>
                                    <TableCell> <b> GREEN </b> </TableCell>
                                    <TableCell align = "center">
                                            <p>L2 Issues</p>
                                    </TableCell>
                                    <TableCell>
                                        <a href = "Link" > 6 </a>
                                    </TableCell>
                                </TableRow>

                                <TableRow>
                                    <TableCell> <b> GREEN </b> </TableCell>
                                    <TableCell align = "center">
                                            <p>L3 Issues</p>
                                    </TableCell>
                                    <TableCell>
                                        <a href = "Link" > 9 </a>
                                    </TableCell>
                                </TableRow>

                                <TableRow>
                                    <TableCell> <b> GREEN </b> </TableCell>
                                    <TableCell align = "center">
                                            <p>Line coverage in main repos</p>
                                    </TableCell>
                                    <TableCell>
                                        <a href = "Link" > 30% </a>
                                    </TableCell>
                                </TableRow>

                                <TableRow>
                                    <TableCell> <b> GREEN </b> </TableCell>
                                    <TableCell align = "center">
                                            <p>Merged PRs with pending Doc tasks</p>
                                    </TableCell>
                                    <TableCell>
                                        <a href = "Link" > 12 </a>
                                    </TableCell>
                                </TableRow>

                                <TableRow>
                                    <TableCell> <b> RED </b> </TableCell>
                                    <TableCell align = "center">
                                            <p>Dependancies where the next version available is smaller than a patch</p>
                                    </TableCell>
                                    <TableCell>
                                        <a href = "Link" > 11 </a>
                                    </TableCell>
                                </TableRow>
                            </TableBody>
                        </Table>
                    </div>
                </div>
            </MuiThemeProvider>
        );
    }
}
 
global.dashboard.registerWidget('HelloWorld', withStyles(styles, {withTheme: true})(HelloWorld));